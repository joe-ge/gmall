package com.joe.gmall.sms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.sms.entity.FlashPromotionSession;
import com.joe.gmall.sms.mapper.FlashPromotionSessionMapper;
import com.joe.gmall.sms.service.FlashPromotionSessionService;
import com.joe.gmall.vo.PageInfoVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 限时购场次表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class FlashPromotionSessionServiceImpl extends ServiceImpl<FlashPromotionSessionMapper, FlashPromotionSession> implements FlashPromotionSessionService {
    @Override
    public void updateStatus(Long id, Integer status) {
        FlashPromotionSession session = new FlashPromotionSession();
        session.setId(id);
        session.setStatus(status);

        baseMapper.updateById(session);
    }

    @Override
    public PageInfoVo selectListForPage(Long flashPromotionId) {
        QueryWrapper<FlashPromotionSession> wrapper =
                new QueryWrapper<FlashPromotionSession>();
        return PageInfoVo.valueOf(baseMapper
                .selectPage(new Page<FlashPromotionSession>(1,100),wrapper));
    }

}
