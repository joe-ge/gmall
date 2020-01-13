package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.HomeAdvertise;
import com.joe.gmall.sms.mapper.HomeAdvertiseMapper;
import com.joe.gmall.sms.service.HomeAdvertiseService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 首页轮播广告表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class HomeAdvertiseServiceImpl extends ServiceImpl<HomeAdvertiseMapper, HomeAdvertise> implements HomeAdvertiseService {

    @Override
    public int updateStatus(Long id, Integer status) {
        HomeAdvertise homeAdvertise = new HomeAdvertise();
        homeAdvertise.setId(id);
        homeAdvertise.setStatus(status);
        return baseMapper.updateById(homeAdvertise);
    }

    @Override
    public PageInfoVo listAdvertiseForPage(String name,
                                           Integer type,
                                           String endTime,
                                           Integer pageSize, Integer pageNum) {

        QueryWrapper<HomeAdvertise> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.lt("end_time", endTime);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }

        IPage<HomeAdvertise> iPage = baseMapper.selectPage(new Page<HomeAdvertise>(pageNum, pageSize), wrapper);

        return PageInfoVo.valueOf(iPage);
    }

}
