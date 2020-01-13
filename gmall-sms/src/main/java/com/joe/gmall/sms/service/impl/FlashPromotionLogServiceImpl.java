package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.FlashPromotionLog;
import com.joe.gmall.sms.mapper.FlashPromotionLogMapper;
import com.joe.gmall.sms.service.FlashPromotionLogService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 限时购通知记录 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class FlashPromotionLogServiceImpl extends ServiceImpl<FlashPromotionLogMapper, FlashPromotionLog> implements FlashPromotionLogService {

}
