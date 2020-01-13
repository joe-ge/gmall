package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.HomeRecommendProduct;
import com.joe.gmall.sms.mapper.HomeRecommendProductMapper;
import com.joe.gmall.sms.service.HomeRecommendProductService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class HomeRecommendProductServiceImpl extends ServiceImpl<HomeRecommendProductMapper, HomeRecommendProduct> implements HomeRecommendProductService {
    @Override
    public void updateSort(Long id, Integer sort) {
        HomeRecommendProduct product = new HomeRecommendProduct();
        product.setId(id);product.setSort(sort);
        baseMapper.updateById(product);
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach((id)->{
            HomeRecommendProduct product = new HomeRecommendProduct();
            product.setId(id);
            product.setRecommendStatus(recommendStatus);
            baseMapper.updateById(product);
        });
    }

    @Override
    public PageInfoVo listrecommendProductForPage(String productName,
                                                  Integer recommendStatus,
                                                  Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeRecommendProduct> wrapper = new QueryWrapper<>();
        IPage<HomeRecommendProduct> iPage = baseMapper.selectPage(new Page<HomeRecommendProduct>(pageNum, pageSize), wrapper);
        return PageInfoVo.valueOf(iPage);
    }

}
