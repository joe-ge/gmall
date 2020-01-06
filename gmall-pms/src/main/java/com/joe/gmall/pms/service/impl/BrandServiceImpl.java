package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.Brand;
import com.joe.gmall.pms.mapper.BrandMapper;
import com.joe.gmall.pms.service.BrandService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Component
@Service(version = "1.0")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Override
    public PageInfoVo brandPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<Brand>().like(!StringUtils.isEmpty(keyword), "name", keyword);
        IPage<Brand> brandPage = baseMapper.selectPage(new Page<Brand>(pageNum.longValue(),
                pageSize.longValue()), queryWrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(brandPage.getTotal(),
                brandPage.getSize(), pageSize.longValue(), brandPage.getRecords(),
                brandPage.getCurrent());
        return pageInfoVo;
    }
}
