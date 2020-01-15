package com.joe.gmall.cart.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import	java.math.BigDecimal;

import java.util.List;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-13
 */
@Setter
@ToString
public class Cart implements Serializable {
    //所有的购物项
    @Getter
    List<CartItemVo> cartItemVos;
    //商品总数
    private Integer count;
    private Integer checkedCount;
    //已选中商品的总价
    private BigDecimal totalPrice;

    public Integer getCount() {
        count = count == null ? 0 : count;
        for (CartItemVo cartItemVo : cartItemVos) {
            this.count += cartItemVo.getCount();
        }
        return this.count;
    }

    public Integer getCheckedCount() {
        checkedCount = checkedCount == null ? 0 : checkedCount;
        for (CartItemVo cartItemVo : cartItemVos) {
            if (cartItemVo.getCheck()) {
                this.checkedCount += cartItemVo.getCount();
            }
        }
        return this.checkedCount;
    }

    public BigDecimal getTotalPrice() {
        totalPrice = totalPrice == null ? BigDecimal.valueOf(0) : totalPrice;
        for (CartItemVo cartItemVo : cartItemVos) {
            if (cartItemVo.getCheck()) {
                this.totalPrice = this.totalPrice.add(cartItemVo.getTotalPrice());
            }
        }
        return this.totalPrice;
    }
}
