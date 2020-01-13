package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.FlashPromotion;
import com.joe.gmall.sms.mapper.FlashPromotionMapper;
import com.joe.gmall.sms.service.FlashPromotionService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 限时购表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class FlashPromotionServiceImpl extends ServiceImpl<FlashPromotionMapper, FlashPromotion> implements FlashPromotionService {
    @Override
    public int createFlashPromotion(FlashPromotion flashPromotion) {
        baseMapper.insert(flashPromotion);
        return 1;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        FlashPromotion flashPromotion = new FlashPromotion();
        flashPromotion.setId(id);
        flashPromotion.setStatus(status);
        baseMapper.updateById(flashPromotion);
        return 1;
    }

    @Override
    public PageInfoVo listflashPromotionForPage(String keyword,
                                                Integer pageSize, Integer pageNum) {

        QueryWrapper<FlashPromotion> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.eq("title", keyword);
        }

        IPage<FlashPromotion> iPage = baseMapper.selectPage(new Page<FlashPromotion>(pageNum, pageSize),
                wrapper);

        return PageInfoVo.valueOf(iPage);
    }

}
