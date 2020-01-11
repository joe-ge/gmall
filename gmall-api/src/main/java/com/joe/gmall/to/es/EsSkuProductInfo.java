package com.joe.gmall.to.es;

import com.joe.gmall.pms.entity.SkuStock;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: gmail
 * @description
 * @author: Joe
 * @create: 2020-01-08
 */
@Data
public class EsSkuProductInfo extends SkuStock implements Serializable {

    //sku的特定标题
    private String skuTitle;
    /**
     * 每个sku特有的属性以及其它的值
     * 颜色
     * 内存
     */
    private List<EsProductAttributeValue> attributeValues;
}
