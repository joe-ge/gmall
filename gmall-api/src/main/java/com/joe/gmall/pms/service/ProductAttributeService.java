package com.joe.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.pms.entity.ProductAttribute;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface ProductAttributeService extends IService<ProductAttribute> {

    PageInfoVo getProductAttribute(Long cid, Integer type, Integer pageSize, Integer pageNum);
}
