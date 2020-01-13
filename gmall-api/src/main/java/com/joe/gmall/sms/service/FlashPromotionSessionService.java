package com.joe.gmall.sms.service;

import com.joe.gmall.sms.entity.FlashPromotionSession;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joe.gmall.vo.PageInfoVo;

/**
 * <p>
 * 限时购场次表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface FlashPromotionSessionService extends IService<FlashPromotionSession> {
    void updateStatus(Long id, Integer status);

    PageInfoVo selectListForPage(Long flashPromotionId);

}
