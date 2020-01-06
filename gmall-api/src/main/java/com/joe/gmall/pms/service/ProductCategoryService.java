package com.joe.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.pms.entity.ProductCategory;
import com.joe.gmall.vo.product.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    /**
     *根据父类id参数查询任意菜单以及他下面的子菜单
     * @param parentId 0表示所有分类及其子分类
     * @return
     */
    List<PmsProductCategoryWithChildrenItem> listProductCategoryWithChildren(Integer parentId);
}
