package com.joe.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.ProductAttributeCategory;
import com.joe.gmall.pms.mapper.ProductAttributeCategoryMapper;
import com.joe.gmall.pms.service.ProductAttributeCategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {

}
