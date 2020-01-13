package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.HomeRecommendSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface HomeRecommendSubjectService extends IService<HomeRecommendSubject> {
    void updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listForPage(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
