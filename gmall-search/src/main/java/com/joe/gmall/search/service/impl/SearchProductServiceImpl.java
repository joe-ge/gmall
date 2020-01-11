package com.joe.gmall.search.service.impl;
import	java.util.HashMap;
import	java.util.ArrayList;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.joe.gmall.constant.EsConstant;
import com.joe.gmall.search.SearchProductService;
import com.joe.gmall.to.es.EsProduct;
import com.joe.gmall.vo.search.SearchParam;
import com.joe.gmall.vo.search.SearchResponse;
import com.joe.gmall.vo.search.SearchResponseAttrVo;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-10
 */
@Slf4j
@Service(version = "1.0")
@Component
public class SearchProductServiceImpl implements SearchProductService {

    @Autowired
    JestClient jestClient;

    @Override
    public SearchResponse serachproduct(SearchParam searchParam) {

        //1.构建检索条件
        String dsl = bulidDsl(searchParam);
        log.debug("从es中检索商品的dsl语句为：\n{}",dsl);

        Search search = new Search.Builder(dsl)
                .addIndex(EsConstant.PRODUCT_ES_INDEX)
                .addType(EsConstant.PRODUCT_INFO_ES_TYPE)
                .build();
        SearchResult searchResult = null;
        try {
            //2.进行检索
            searchResult = jestClient.execute(search);
            log.debug("从es中的检索出的商品结果：{}" , searchResult.getHits(EsProduct.class));
        } catch (IOException e) {
//            log.debug("从es中检索商品失败：{}",e.getMessage());
//            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SearchResponse response = buildSearchResponse(searchResult);
        response.setPageNum(searchParam.getPageNum());
        response.setPageSize(searchParam.getPageSize());
        return response;
    }

    private SearchResponse buildSearchResponse(SearchResult searchResult) {
        SearchResponse response = new SearchResponse();

        MetricAggregation aggregations = searchResult.getAggregations();

        //可供选择的品牌
        TermsAggregation brandAgg = aggregations.getTermsAggregation("brand_agg");
        /**********该处没有提取品牌ID,待后续完善************/
        List<String> brands = new ArrayList<String>(10);
        brandAgg.getBuckets().forEach((bucket)->{
            brands.add(bucket.getKeyAsString());
        });
        SearchResponseAttrVo brandAttrVo = new SearchResponseAttrVo();
        brandAttrVo.setName("品牌");
        brandAttrVo.setValue(brands);
        response.setBrand(brandAttrVo);

        //可供选择的分类
        TermsAggregation categoryAgg = aggregations.getTermsAggregation("category_agg");
        List<String> categoryValues = new ArrayList<String>(20);
        categoryAgg.getBuckets().forEach((bucket)->{
            List<TermsAggregation.Entry> categoryIdAgg = bucket.getTermsAggregation("categoryId_agg").getBuckets();
            String categoryId = categoryIdAgg.get(0).getKeyAsString();
            Map<String,String> map=new HashMap<String, String> (2);
            map.put("id", categoryId);
            map.put("name", bucket.getKeyAsString());
            String cateInfo = JSON.toJSONString(map);
            categoryValues.add(cateInfo);
        });
        SearchResponseAttrVo categoryAttrVo = new SearchResponseAttrVo();
        categoryAttrVo.setName("分类");
        categoryAttrVo.setValue(categoryValues);
        response.setCatelog(categoryAttrVo);

        //所有可供筛选的属性
        TermsAggregation attrNameAgg = aggregations.getChildrenAggregation("attr_agg").getTermsAggregation("attrName_agg");
        List<SearchResponseAttrVo> attrVoList = new ArrayList<>();
        attrNameAgg.getBuckets().forEach((bucket) -> {
            SearchResponseAttrVo attrVo = new SearchResponseAttrVo();
            //属性的名字
            String attrName = bucket.getKeyAsString();
            attrVo.setName(attrName);
            //属性的ID
            TermsAggregation attrIdAgg = bucket.getTermsAggregation("attrId_agg");
            String attrId = attrIdAgg.getBuckets().get(0).getKeyAsString();
            attrVo.setProductAttributeId(Long.valueOf(attrId));
            //属性所涉及的所有值
            TermsAggregation attrValueAgg = bucket.getTermsAggregation("attrValue_agg");
            List<String> valueList = new ArrayList<> ();
            attrValueAgg.getBuckets().forEach((valueBucket) -> {
                valueList.add(valueBucket.getKeyAsString());
            });
            attrVo.setValue(valueList);
            attrVoList.add(attrVo);

        });
        response.setAttrs(attrVoList);


        //查出的数据
        List<SearchResult.Hit<EsProduct, Void>> hits = searchResult.getHits(EsProduct.class);
        List<EsProduct> esProducts = new ArrayList<>(20);
        hits.forEach((hit)->{
            EsProduct source = hit.source;
            //提取高亮结果
            Map<String, List<String>> highlight = hit.highlight;
            String highlightTitle = null;
            if (highlight != null && highlight.get("skuProductInfos.skuTitle").size()>0) {
                highlightTitle = highlight.get("skuProductInfos.skuTitle").get(0);
            }
            //设置高亮结果
            if (highlightTitle != null) {
                source.setName(highlightTitle);
            }
            esProducts.add(source);
        });
        response.setProducts(esProducts);

        response.setTotal(searchResult.getTotal());
        return response;
    }

    private String bulidDsl(SearchParam searchParam) {
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        //1.查询
        //1.1 检索
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("skuProductInfos.skuTitle", searchParam.getKeyword());
            NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("skuProductInfos", matchQuery, ScoreMode.None);
            boolQuery.must(nestedQuery);
        }
        //1.2 过滤
        if (ArrayUtils.isNotEmpty(searchParam.getCatelog3())) {
            //按照三级分类过滤
            TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("productCategoryId", searchParam.getCatelog3());
            boolQuery.filter(termsQuery);
        }
        if (ArrayUtils.isNotEmpty(searchParam.getBrand())) {
            //按照品牌过滤
            TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("brandName.keyword", searchParam.getBrand());
            boolQuery.filter(termsQuery);
        }
        //1.2.1 按照属性过滤、按照品牌过滤、按照分类过滤
        if (ArrayUtils.isNotEmpty(searchParam.getProps())) {
            String[] props = searchParam.getProps();
            for (String prop : props) {
                String[] split = prop.split(":");
                //格式：  2:4g-3g
                BoolQueryBuilder boolQuery1 = QueryBuilders.boolQuery();

                List<QueryBuilder> queryList = new ArrayList<QueryBuilder>(2);
                queryList.add(QueryBuilders.matchQuery("attrValueList.productAttributeId", split[0]));
                queryList.add(QueryBuilders.termsQuery("attrValueList.value.keyword", split[1].split("-")));
                boolQuery1.must().addAll(queryList);

                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrValueList", boolQuery1, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }
        if (searchParam.getPriceFrom() != null || searchParam.getPriceTo() != null) {
            RangeQueryBuilder priceRangeQuery = QueryBuilders.rangeQuery("price");
            if (searchParam.getPriceFrom() != null) {
                priceRangeQuery.from(searchParam.getPriceFrom());
            }
            if (searchParam.getPriceTo() != null) {
                priceRangeQuery.to(searchParam.getPriceTo());
            }
            boolQuery.filter(priceRangeQuery);
        }
        searchBuilder.query(boolQuery);
        //2 高亮
        if (!StringUtils.isEmpty(searchParam.getKeyword())) {
            HighlightBuilder hits = new HighlightBuilder();
            hits.field("skuProductInfos.skuTitle")
                    .preTags("<b style='color: red'>")
                    .postTags("</b>");
            searchBuilder.highlighter(hits);
        }
        //3 聚合
        //按照品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg").field("brandName.keyword");
        brandAgg.subAggregation(AggregationBuilders.terms("brandId").field("brandId"));
        searchBuilder.aggregation(brandAgg);
        //按照分类聚合
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("category_agg").field("productCategoryName.keyword");
        categoryAgg.subAggregation(AggregationBuilders.terms("categoryId_agg").field("productCategoryId"));
        searchBuilder.aggregation(categoryAgg);
        //按照属性聚合
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attr_agg", "attrValueList");
        TermsAggregationBuilder attrNameAgg = AggregationBuilders.terms("attrName_agg").field("attrValueList.name");
        attrNameAgg.subAggregation(AggregationBuilders.terms("attrValue_agg").field("attrValueList.value.keyword"));
        attrNameAgg.subAggregation(AggregationBuilders.terms("attrId_agg").field("attrValueList.productAttributeId"));
        attrAgg.subAggregation(attrNameAgg);
        searchBuilder.aggregation(attrAgg);
        //4 分页
        searchBuilder.from((searchParam.getPageNum() - 1) * searchParam.getPageSize());
        searchBuilder.size(searchParam.getPageSize());
        //5 排序
        if (!StringUtils.isEmpty(searchParam.getOrder())) {
            // order=1:asc  排序规则
            //0：综合排序 (默认顺序(评分规则)) 1：销量  2：价格
            String[] split = searchParam.getOrder().split(":");
            if ("1".equals(split[0])){//销量
                FieldSortBuilder saleSort = SortBuilders.fieldSort("sale");
                if ("asc".equalsIgnoreCase(split[1])) {
                    saleSort.order(SortOrder.ASC);
                } else {
                    saleSort.order(SortOrder.DESC);
                }
                searchBuilder.sort(saleSort);
            }else if ("2".equals(split[0])){//价格
                FieldSortBuilder priceSort = SortBuilders.fieldSort("price");
                if ("asc".equalsIgnoreCase(split[1])) {
                    priceSort.order(SortOrder.ASC);
                } else {
                    priceSort.order(SortOrder.DESC);
                }
                searchBuilder.sort(priceSort);
            }
        }
        return searchBuilder.toString();
    }

}
