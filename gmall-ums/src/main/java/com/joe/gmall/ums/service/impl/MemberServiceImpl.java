package com.joe.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.ums.entity.Member;
import com.joe.gmall.ums.mapper.MemberMapper;
import com.joe.gmall.ums.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service(version = "1.0")
@Component
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Member login(String username, String password) {
        String passwordMd5Digest = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<Member>().eq("username", username).eq("password", passwordMd5Digest);
        Member member = baseMapper.selectOne(memberQueryWrapper);
        return member;
    }
}
