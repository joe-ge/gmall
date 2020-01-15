package com.joe.gmall.cart.vo;

import lombok.Data;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
@Data
public class UserCartKey {
    private boolean isLogin;
    //如果登陆用户的id
    private Long userId;
    //用户没有登陆且没有临时购物车的返回的key
    private String tempCartKey;
    //最终用哪个购物车（加前缀的完整key）
    private String finalCartKey;
}
