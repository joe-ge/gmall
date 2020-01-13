package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.CouponHistory;
import com.joe.gmall.sms.mapper.CouponHistoryMapper;
import com.joe.gmall.sms.service.CouponHistoryService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryMapper, CouponHistory> implements CouponHistoryService {


    @Override
    public PageInfoVo listCouponHistoryForPage(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum) {
        QueryWrapper<CouponHistory> wrapper = new QueryWrapper<>();
        if(couponId!=null){
            wrapper.eq("coupon_id",couponId);
        }
        if(useStatus!=null){
            wrapper.eq("use_status",useStatus);
        }
        if(!StringUtils.isEmpty(orderSn)){
            wrapper.eq("order_sn",orderSn);
        }
        IPage<CouponHistory> iPage = baseMapper.selectPage(new Page<CouponHistory>(pageNum, pageSize), wrapper);
        return PageInfoVo.valueOf(iPage);
    }


}
