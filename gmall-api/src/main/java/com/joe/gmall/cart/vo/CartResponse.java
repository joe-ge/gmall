package com.joe.gmall.cart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
@Data
public class CartResponse implements Serializable {
    private Cart cart;
    private CartItemVo cartItemVo;
    private String cartKey;
}
