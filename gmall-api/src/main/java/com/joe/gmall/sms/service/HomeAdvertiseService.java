package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.HomeAdvertise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface HomeAdvertiseService extends IService<HomeAdvertise> {
    int updateStatus(Long id, Integer status);

    PageInfoVo listAdvertiseForPage(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);

}
