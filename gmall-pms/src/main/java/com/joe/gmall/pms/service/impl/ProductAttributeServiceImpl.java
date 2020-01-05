package com.joe.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.ProductAttribute;
import com.joe.gmall.pms.mapper.ProductAttributeMapper;
import com.joe.gmall.pms.service.ProductAttributeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {

}
