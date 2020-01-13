package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;
import com.joe.gmall.vo.sms.SmsCouponParam;

/**
 * <p>
 * 优惠卷表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface CouponService extends IService<Coupon> {
    /**
     * 创建优惠卷
     * @param couponParam
     * @return
     */
    int create(SmsCouponParam couponParam);

    /**
     * 修改优惠卷信息
     * @param id
     * @param couponParam
     * @return
     */
    int updateCouponInfos(Long id, SmsCouponParam couponParam);

    /**
     * 分页查询优惠券信息
     * @param name
     * @param type
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listForPage(String name, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 查询单个优惠券信息
     * @param id
     * @return
     */
    SmsCouponParam getCouponItemInfo(Long id);


}
