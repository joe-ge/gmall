package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.*;
import com.joe.gmall.pms.mapper.*;
import com.joe.gmall.pms.service.ProductService;
import com.joe.gmall.vo.PageInfoVo;
import com.joe.gmall.vo.product.PmsProductParam;
import com.joe.gmall.vo.product.PmsProductQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Slf4j
@Component
@Service(version = "1.0")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    ProductFullReductionMapper productFullReductionMapper;

    @Autowired
    ProductLadderMapper productLadderMapper;

    @Autowired
    SkuStockMapper skuStockMapper;

    @Override
    public boolean saveProduct(PmsProductParam productParam) {

        //pms_product：基本信息
        Product product = new Product();
        BeanUtils.copyProperties(productParam,product);
        baseMapper.insert(product);
        //mybatisplus可以自动获取自增id
        Long productId = product.getId();
        log.debug("商品的自增id:{}",productId);

        //pms_product_attribute_value:所有属性的值
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach(productAttributeValue ->{
            productAttributeValue.setProductId(productId);
            productAttributeValueMapper.insert(productAttributeValue);
        });

        //pms_product_full_reduction:满减信息
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach(productFullReduction -> {
            productFullReduction.setProductId(productId);
            productFullReductionMapper.insert(productFullReduction);
        });

        //pms_product_ladder:满减表
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach(productLadder -> {
            productLadder.setProductId(productId);
            productLadderMapper.insert(productLadder);
        });

        //pms_sku_stock:sku库存表
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 1; i <= skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i-1);
            //skuCode必须有,生成规则 pid_sku自增id
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                skuStock.setSkuCode(productId + "_" + i);
            }
            skuStock.setProductId(productId);
            skuStockMapper.insert(skuStock);
        }

        return true;
    }

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<Product>()
                .eq("delete_status",new Integer(0));
        if (!StringUtils.isEmpty(param.getKeyword())) {
            queryWrapper.like("name", param.getKeyword());
        }
        if (param.getBrandId() != null) {
            queryWrapper.eq("brand_id", param.getBrandId());
        }
        if (param.getProductCategoryId() != null) {
            queryWrapper.eq("product_category_id", param.getProductCategoryId());
        }
        if (!StringUtils.isEmpty(param.getProductSn())) {
            queryWrapper.like("product_sn" , param.getProductSn());
        }
        if (param.getPublishStatus() != null) {
            queryWrapper.eq("publish_status" , param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null) {
            queryWrapper.eq("verify_status", param.getVerifyStatus());
        }
        IPage<Product> page = baseMapper.selectPage(new Page<Product>(param.getPageNum(), param.getPageSize()), queryWrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }
}
