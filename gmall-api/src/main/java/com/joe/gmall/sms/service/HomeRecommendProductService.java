package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.HomeRecommendProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

import java.util.List;

/**
 * <p>
 * 人气推荐商品表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface HomeRecommendProductService extends IService<HomeRecommendProduct> {
    void updateSort(Long id, Integer sort);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    PageInfoVo listrecommendProductForPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
