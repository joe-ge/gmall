package com.joe.gmall.constant;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
public class CartConstant {
    //后面加cartKey
    public static final String TEMP_CART_KEY_PREFIX = "cart:temp:";
    //后面加用户id
    public static final String USER_CART_KEY_PREFIX = "cart:user:";
    //购物车在redis中的保存时间，15天
    public static final Long CART_KEY_EXPIRE = 15L;
    //购物车在redis中记录选中商品的的key
    public static final String CART_CHECKED_KEY = "checked";
}
