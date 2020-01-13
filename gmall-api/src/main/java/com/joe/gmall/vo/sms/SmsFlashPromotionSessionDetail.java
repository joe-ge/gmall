package com.joe.gmall.vo.sms;

import com.joe.gmall.sms.entity.FlashPromotionSession;
import lombok.Getter;
import lombok.Setter;

/**
 * 包含商品数量的场次信息
 */
public class SmsFlashPromotionSessionDetail extends FlashPromotionSession {
    @Setter
    @Getter
    private Integer productCount;
}
