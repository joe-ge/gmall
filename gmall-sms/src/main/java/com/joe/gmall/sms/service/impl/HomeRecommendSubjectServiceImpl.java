package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.HomeRecommendSubject;
import com.joe.gmall.sms.mapper.HomeRecommendSubjectMapper;
import com.joe.gmall.sms.service.HomeRecommendSubjectService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class HomeRecommendSubjectServiceImpl extends ServiceImpl<HomeRecommendSubjectMapper, HomeRecommendSubject> implements HomeRecommendSubjectService {
    @Override
    public void updateSort(Long id, Integer sort) {
        HomeRecommendSubject subject = new HomeRecommendSubject();
        subject.setId(id);subject.setSort(sort);
        baseMapper.updateById(subject);
    }

    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach((id)->{
            HomeRecommendSubject subject = new HomeRecommendSubject();
            subject.setId(id);subject.setRecommendStatus(recommendStatus);
            baseMapper.updateById(subject);
        });
    }

    @Override
    public PageInfoVo listForPage(String subjectName,
                                  Integer recommendStatus,
                                  Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeRecommendSubject> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(subjectName)){
            wrapper.like("subject_name",subjectName);
        }
        if(recommendStatus!=null){
            wrapper.eq("recommend_status",recommendStatus);
        }


        IPage<HomeRecommendSubject> iPage = baseMapper.selectPage(new Page<HomeRecommendSubject>(pageNum, pageSize), wrapper);
        return  PageInfoVo.valueOf(iPage);
    }

}
