package com.joe.gmall.search;

import com.joe.gmall.vo.search.SearchParam;
import com.joe.gmall.vo.search.SearchResponse;

/**
 * @program: gmall
 * @description 商品检索服务
 * @author: Joe
 * @create: 2020-01-10
 */
public interface SearchProductService {
    SearchResponse serachproduct(SearchParam searchParam);
}
