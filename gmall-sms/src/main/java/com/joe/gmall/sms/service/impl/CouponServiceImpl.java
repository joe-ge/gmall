package com.joe.gmall.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.Coupon;
import com.joe.gmall.sms.mapper.CouponMapper;
import com.joe.gmall.sms.service.CouponService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠卷表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

}
