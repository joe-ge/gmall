package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.ProductAttribute;
import com.joe.gmall.pms.mapper.ProductAttributeMapper;
import com.joe.gmall.pms.service.ProductAttributeService;
import com.joe.gmall.utils.PageUtils;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {
    @Override
    public PageInfoVo getProductAttribute(Long cid, Integer type, Integer pageSize, Integer pageNum) {

        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<ProductAttribute>()
                .eq("product_attribute_category_id",cid)
                .eq("type",type);

        IPage<ProductAttribute> page = baseMapper.selectPage(PageUtils.construct(ProductAttribute.class, pageNum, pageSize), queryWrapper);
        return PageInfoVo.valueOf(page);
    }
}
