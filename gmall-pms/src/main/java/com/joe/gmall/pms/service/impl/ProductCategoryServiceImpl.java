package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.ProductCategory;
import com.joe.gmall.pms.mapper.ProductCategoryMapper;
import com.joe.gmall.pms.service.ProductCategoryService;
import com.joe.gmall.vo.product.PmsProductCategoryWithChildrenItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Component
@Service(version = "1.0")
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public List<PmsProductCategoryWithChildrenItem> listProductCategoryWithChildren(Integer parentId) {
        return productCategoryMapper.listProductCategoryWithChildren(parentId);
    }
}
