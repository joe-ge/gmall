package com.joe.gmall.cart.vo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import	java.math.BigDecimal;



/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-13
 */
@Setter
@ToString
public class CartItemVo implements Serializable {
    @Getter
    private Long skuId;
    //购物项的基本信息
    @Getter
    private String name;
    @Getter
    private String skuCode;
    @Getter
    private Integer stock;
    @Getter
    private String sp1;
    @Getter
    private String sp2;
    @Getter
    private String sp3;
    @Getter
    @NotNull
    private BigDecimal price;
    @Getter
    private BigDecimal promotionPrice;

    @Getter
    @NotNull
    private Integer count;
    @Getter
    //当前购物项的选中状态
    private boolean check = true;
    //当前购物项的总价
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        price = price == null ? BigDecimal.ZERO : price;
        totalPrice = price.multiply(new BigDecimal(count == null ? 0 : count));
        return totalPrice;
    }
}
