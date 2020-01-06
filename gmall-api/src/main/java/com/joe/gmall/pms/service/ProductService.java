package com.joe.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.pms.entity.Product;
import com.joe.gmall.vo.PageInfoVo;
import com.joe.gmall.vo.product.PmsProductQueryParam;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据复杂条件查询分页
     * @param param
     * @return
     */
    PageInfoVo productPageInfo(PmsProductQueryParam param);
}
