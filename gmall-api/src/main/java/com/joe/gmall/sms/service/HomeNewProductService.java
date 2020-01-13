package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.HomeNewProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface HomeNewProductService extends IService<HomeNewProduct> {
    PageInfoVo listNewProductForPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    void updateSort(Long id, Integer sort);

}
