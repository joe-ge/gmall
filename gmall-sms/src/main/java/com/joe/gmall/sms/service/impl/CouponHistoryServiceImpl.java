package com.joe.gmall.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.CouponHistory;
import com.joe.gmall.sms.mapper.CouponHistoryMapper;
import com.joe.gmall.sms.service.CouponHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryMapper, CouponHistory> implements CouponHistoryService {

}
