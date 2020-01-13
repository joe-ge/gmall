package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.CouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 优惠券使用、领取历史表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface CouponHistoryService extends IService<CouponHistory> {
    /**
     * 分页获取优惠券领取记录
     * @param couponId
     * @param useStatus
     * @param orderSn
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listCouponHistoryForPage(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
