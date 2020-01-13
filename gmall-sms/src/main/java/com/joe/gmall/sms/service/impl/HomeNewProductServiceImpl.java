package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.HomeNewProduct;
import com.joe.gmall.sms.mapper.HomeNewProductMapper;
import com.joe.gmall.sms.service.HomeNewProductService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class HomeNewProductServiceImpl extends ServiceImpl<HomeNewProductMapper, HomeNewProduct> implements HomeNewProductService {
    @Override
    public PageInfoVo listNewProductForPage(String productName,
                                            Integer recommendStatus,
                                            Integer pageSize, Integer pageNum) {

        QueryWrapper<HomeNewProduct> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(productName)){
            wrapper.like("product_name",productName);
        }
        if(!StringUtils.isEmpty(productName)){
            wrapper.eq("recommend_status",recommendStatus);
        }

        IPage<HomeNewProduct> iPage = baseMapper.selectPage(new Page<HomeNewProduct>(pageNum, pageSize), wrapper);
        return PageInfoVo.valueOf(iPage);
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {

        ids.forEach((id)->{
            HomeNewProduct product = new HomeNewProduct();
            product.setId(id);
            product.setRecommendStatus(recommendStatus);
            baseMapper.updateById(product);
        });
    }

    @Override
    public void updateSort(Long id, Integer sort) {
        HomeNewProduct product = new HomeNewProduct();
        product.setId(id);
        product.setSort(sort);
        baseMapper.updateById(product);
    }

}
