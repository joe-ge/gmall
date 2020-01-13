package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.CouponProductRelation;
import com.joe.gmall.sms.mapper.CouponProductRelationMapper;
import com.joe.gmall.sms.service.CouponProductRelationService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 优惠券和产品的关系表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class CouponProductRelationServiceImpl extends ServiceImpl<CouponProductRelationMapper, CouponProductRelation> implements CouponProductRelationService {

}
