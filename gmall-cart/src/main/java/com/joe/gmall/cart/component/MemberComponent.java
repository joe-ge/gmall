package com.joe.gmall.cart.component;

import com.alibaba.fastjson.JSON;
import com.joe.gmall.cart.vo.UserCartKey;
import com.joe.gmall.constant.CartConstant;
import com.joe.gmall.constant.SysCacheConstant;
import com.joe.gmall.ums.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
@Component
public class MemberComponent {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 跟据accessToken查询用户信息
     * @param accessToken
     * @return
     */
    public Member getMemberByAccessToken(String accessToken) {
        String memberJson = stringRedisTemplate.opsForValue().get(SysCacheConstant.LOGIN_MEMBER + accessToken);
        Member member = JSON.parseObject(memberJson, Member.class);
        return member;
    }

    public UserCartKey getCartKey(String accessToken, String cartKey) {
        //根据accessToken获取用户ID
        UserCartKey userCartKey = new UserCartKey();
        Member member = null;
        if (accessToken != null) {
            member = getMemberByAccessToken(accessToken);
        }
        if (member != null ) {
            //在线购物车
            userCartKey.setLogin(true);
            userCartKey.setUserId(member.getId());
            userCartKey.setFinalCartKey(CartConstant.USER_CART_KEY_PREFIX + member.getId());
            return userCartKey;
        }else if (!StringUtils.isEmpty(cartKey)) {
            //离线购物车
            userCartKey.setLogin(false);
            userCartKey.setFinalCartKey(CartConstant.TEMP_CART_KEY_PREFIX + cartKey);
            return userCartKey;
        }else {
            String temp = UUID.randomUUID().toString().replace("-", "");
            userCartKey.setLogin(false);
            userCartKey.setTempCartKey(temp);
            userCartKey.setFinalCartKey(CartConstant.TEMP_CART_KEY_PREFIX + temp);
            return userCartKey;
        }

    }
}
