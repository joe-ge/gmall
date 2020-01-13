package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.Coupon;
import com.joe.gmall.sms.entity.CouponProductCategoryRelation;
import com.joe.gmall.sms.entity.CouponProductRelation;
import com.joe.gmall.sms.mapper.CouponMapper;
import com.joe.gmall.sms.mapper.CouponProductCategoryRelationMapper;
import com.joe.gmall.sms.mapper.CouponProductRelationMapper;
import com.joe.gmall.sms.service.CouponService;
import com.joe.gmall.vo.PageInfoVo;
import com.joe.gmall.vo.sms.SmsCouponParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 优惠卷表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Component
@Service(version = "1.0")
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    @Autowired
    CouponProductCategoryRelationMapper productCategoryRelationMapper;

    @Autowired
    CouponProductRelationMapper relationMapper;
    @Override
    public int create(SmsCouponParam couponParam) {
        List<CouponProductCategoryRelation> relationList = couponParam.getProductCategoryRelationList();
        List<CouponProductRelation> list = couponParam.getProductRelationList();
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponParam,coupon);
        baseMapper.insert(coupon);

        relationList.forEach((relation)->{
            relation.setCouponId(coupon.getId());
            productCategoryRelationMapper.insert(relation);
        });

        list.forEach((relation)->{
            relation.setCouponId(coupon.getId());
            relationMapper.insert(relation);
        });

        return 1;
    }

    @Override
    public int updateCouponInfos(Long id, SmsCouponParam couponParam) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponParam,coupon);
        coupon.setId(id);
        return baseMapper.updateById(coupon);
    }

    @Override
    public PageInfoVo listForPage(String name, Integer type,
                                  Integer pageSize, Integer pageNum) {
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.eq("name",name);
        }
        if(type!=null){
            wrapper.eq("type",type);
        }
        IPage<Coupon> iPage = baseMapper.selectPage(new Page<Coupon>(pageNum, pageSize), wrapper);
        return PageInfoVo.valueOf(iPage);
    }

    @Override
    public SmsCouponParam getCouponItemInfo(Long id) {
        return null;
    }

}
