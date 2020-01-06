package com.joe.gmall.vo.product;


import com.joe.gmall.pms.entity.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
@NoArgsConstructor
public class PmsProductCategoryWithChildrenItem extends ProductCategory {
    private List<PmsProductCategoryWithChildrenItem> children;

}
