package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.ProductAttributeCategory;
import com.joe.gmall.pms.mapper.ProductAttributeCategoryMapper;
import com.joe.gmall.pms.service.ProductAttributeCategoryService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 产品属性分类表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements ProductAttributeCategoryService {
    @Override
    public PageInfoVo getPageInfo(Integer pageNum, Integer pageSize) {
        IPage<ProductAttributeCategory> page = baseMapper.selectPage(new Page<ProductAttributeCategory>(pageNum.longValue(), pageSize.longValue()), null);
        return PageInfoVo.valueOf(page);
    }
}
