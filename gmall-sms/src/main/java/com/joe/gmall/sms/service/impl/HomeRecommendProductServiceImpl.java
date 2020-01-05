package com.joe.gmall.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.HomeRecommendProduct;
import com.joe.gmall.sms.mapper.HomeRecommendProductMapper;
import com.joe.gmall.sms.service.HomeRecommendProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人气推荐商品表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class HomeRecommendProductServiceImpl extends ServiceImpl<HomeRecommendProductMapper, HomeRecommendProduct> implements HomeRecommendProductService {

}
