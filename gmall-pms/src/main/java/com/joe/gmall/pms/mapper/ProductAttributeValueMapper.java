package com.joe.gmall.pms.mapper;

import com.joe.gmall.pms.entity.ProductAttribute;
import com.joe.gmall.pms.entity.ProductAttributeValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joe.gmall.to.es.EsProductAttributeValue;

import java.util.List;

/**
 * <p>
 * 存储产品参数信息的表 Mapper 接口
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface ProductAttributeValueMapper extends BaseMapper<ProductAttributeValue> {


    /**
     * 查询商品的筛选属性（公共属性）
     * eg:SPU的属性;网络制式：3G 4G 5G，操作系统：Android IO
     * @param id
     * @return
     */
    List<EsProductAttributeValue> selectEsProductAttributeValue(Long id);

    List<ProductAttribute> selectProductSaleAttrName(Long id);
}
