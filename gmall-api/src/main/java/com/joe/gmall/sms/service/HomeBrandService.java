package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.HomeBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface HomeBrandService extends IService<HomeBrand> {
    int updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listBrandForPage(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
