package com.joe.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.pms.entity.ProductAttributeCategory;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface ProductAttributeCategoryService extends IService<ProductAttributeCategory> {

    PageInfoVo getPageInfo(Integer pageNum, Integer pageSize);

}
