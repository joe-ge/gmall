package com.joe.gmall.sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.joe.gmall.constant.SysCacheConstant;
import com.joe.gmall.to.CommonResult;
import com.joe.gmall.ums.entity.Member;
import com.joe.gmall.ums.service.MemberService;
import com.joe.gmall.vo.ums.LoginResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-13
 */
@Controller
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Reference(version = "1.0")
    MemberService memberService;

    @ResponseBody
    @PostMapping("/applogin")
    public CommonResult loginForGmall(@RequestParam("username") String username,
                                      @RequestParam("password") String password){
        CommonResult commonResult = new CommonResult();
        Member member = memberService.login(username, password);
        if (member == null) {
            return commonResult.failed("用户名或密码错误");
        }else{
            String token = UUID.randomUUID().toString().replace("-", "");
            redisTemplate.opsForValue().set(SysCacheConstant.LOGIN_MEMBER + token, JSON.toJSONString(member),
                    SysCacheConstant.LOGIN_MEMBER_TIMEOUT, TimeUnit.MINUTES);

            LoginResponseVo loginResponseVo = new LoginResponseVo();
            BeanUtils.copyProperties(member, loginResponseVo);
            //设置访问令牌
            loginResponseVo.setAccessToken(token);
            return commonResult.success(loginResponseVo);
        }

    }

    @ResponseBody
    @PostMapping("/userinfo")
    public CommonResult getUserInfo(@RequestParam("accessToken") String accessToken) {
        String memberJson =redisTemplate.opsForValue().get(SysCacheConstant.LOGIN_MEMBER + accessToken);
        if (memberJson != null) {
            Member member = JSON.parseObject(memberJson, Member.class);
            member.setId(null);
            member.setPassword(null);
            return new CommonResult().success(member);
        }
        return new CommonResult().unauthorized("验证失败");
    }
}
