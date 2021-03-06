package com.joe.gmall.ums.service;

import com.joe.gmall.ums.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
public interface MemberService extends IService<Member> {

    Member login(String username, String password);
}
