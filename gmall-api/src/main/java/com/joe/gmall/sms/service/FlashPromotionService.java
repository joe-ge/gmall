package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.FlashPromotion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 限时购表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface FlashPromotionService extends IService<FlashPromotion> {

    /**
     * 创建限时购活动
     * @param flashPromotion
     * @return
     */
    int createFlashPromotion(FlashPromotion flashPromotion);

    /**
     * 更新活动状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 分页查询
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfoVo listflashPromotionForPage(String keyword, Integer pageSize, Integer pageNum);

}
