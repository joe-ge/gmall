package com.joe.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.pms.entity.Brand;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface BrandService extends IService<Brand> {

    PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize);
}

