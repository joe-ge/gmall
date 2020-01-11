package com.joe.gmall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.joe.gmall.search.SearchProductService;
import com.joe.gmall.vo.search.SearchParam;
import com.joe.gmall.vo.search.SearchResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gmall
 * @description 商品检索的controller
 * @author: Joe
 * @create: 2020-01-10
 */
@CrossOrigin
@RestController
public class ProductSearchController {

    @Reference(version = "1.0")
    SearchProductService searchProductService;

    @GetMapping("/search")
    public SearchResponse productSerachResponce(SearchParam searchParam) {
        /**
         * 检索商品
         */
        SearchResponse response = searchProductService.serachproduct(searchParam);
        return response;
    }
}
