package com.joe.gmall.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.ums.entity.MemberLoginLog;
import com.joe.gmall.ums.mapper.MemberLoginLogMapper;
import com.joe.gmall.ums.service.MemberLoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员登录记录 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {

}
