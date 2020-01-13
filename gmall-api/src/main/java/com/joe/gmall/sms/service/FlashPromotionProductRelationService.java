package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.FlashPromotionProductRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 商品限时购与商品关系表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface FlashPromotionProductRelationService extends IService<FlashPromotionProductRelation> {
    /**
     * 分页查询不同场次关联及商品信息
     * @param flashPromotionId
     * @param flashPromotionSessionId
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listRelationForPage(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);

}
