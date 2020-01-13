package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.FlashPromotionProductRelation;
import com.joe.gmall.sms.mapper.FlashPromotionProductRelationMapper;
import com.joe.gmall.sms.service.FlashPromotionProductRelationService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品限时购与商品关系表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class FlashPromotionProductRelationServiceImpl extends ServiceImpl<FlashPromotionProductRelationMapper, FlashPromotionProductRelation> implements FlashPromotionProductRelationService {
    @Override
    public PageInfoVo listRelationForPage(Long flashPromotionId,
                                          Long flashPromotionSessionId,
                                          Integer pageSize, Integer pageNum) {

        QueryWrapper<FlashPromotionProductRelation> wrapper = new QueryWrapper<>();
        if (flashPromotionId != null) {
            wrapper.eq("flash_promotion_id", flashPromotionId);
        }
        if (flashPromotionSessionId != null) {
            wrapper.eq("flash_promotion_session_id", flashPromotionSessionId);
        }
        IPage<FlashPromotionProductRelation> iPage = baseMapper
                .selectPage(new Page<FlashPromotionProductRelation>(pageNum, pageSize),
                        wrapper);
        return PageInfoVo.valueOf(iPage);
    }

}
