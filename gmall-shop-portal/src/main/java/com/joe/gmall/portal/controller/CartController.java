package com.joe.gmall.portal.controller;
import	java.security.acl.Group;

import com.alibaba.dubbo.config.annotation.Reference;
import com.joe.gmall.cart.service.CartService;
import com.joe.gmall.cart.vo.CartResponse;
import com.joe.gmall.to.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ExecutionException;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
@Api("购物车模块")
@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference(version = "1.0",parameters = {
            "addToCart.retries","0"
    })
    CartService cartService;

    @PostMapping("/add")
    public CommonResult addToCart(@RequestParam("skuId") Long skuId,
                                  @RequestParam(value = "count",defaultValue = "1") Integer count,
                                  @RequestParam(value = "cartKey", required = false) String cartKey,
                                  @RequestParam(value = "accessToken",required = false) String accessToken) throws ExecutionException, InterruptedException {
        CartResponse cartResponse = cartService.addToCart(skuId,count,cartKey,accessToken);
        return new CommonResult().success(cartResponse);
    }

    @ApiOperation("更新购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", value = "商品的skuId"),
            @ApiImplicitParam(name = "count", value = "数量",defaultValue = "1"),
            @ApiImplicitParam(name = "cartKey", value = "离线购物车key,可以没有"),
            @ApiImplicitParam(name = "accessToken", value = "登陆后的访问令牌，没登录不用传"),
    })
    @PostMapping("/update")
    public CommonResult updateCartItemNum(@RequestParam("skuId") Long skuId,
                                          @RequestParam(value = "count", defaultValue = "1") Integer count,
                                          @RequestParam(value = "cartKey", required = false) String cartKey,
                                          @RequestParam(value = "accessToken", required = false) String accessToken) throws ExecutionException, InterruptedException {

        CartResponse cartResponse = cartService.updateCartItemNum(skuId, count, cartKey, accessToken);
        return new CommonResult().success(cartResponse);
    }

    /**
     * 查看购物车
     * @param cartKey
     * @param accessToken
     * @return
     */
    @GetMapping("/list")
    public CommonResult cartList(@RequestParam(value = "cartKey", required = false) String cartKey,
                                 @RequestParam(value = "accessToken",required = false) String accessToken) throws ExecutionException, InterruptedException {
        CartResponse cartResponse = cartService.listCart(cartKey, accessToken);
        return new CommonResult().success(cartResponse);
    }

    /**
     * 删除
     * @param cartKey
     * @param accessToken
     * @return
     */
    @GetMapping("/del")
    public CommonResult cartDel(@RequestParam("skuId") Long skuId,
                                @RequestParam(value = "cartKey", required = false) String cartKey,
                                @RequestParam(value = "accessToken",required = false) String accessToken) throws ExecutionException, InterruptedException {
        CartResponse cartResponse = cartService.deleteCart(skuId,cartKey,accessToken);
        return new CommonResult().success(cartResponse);
    }

    /**
     * 清空购物车
     * @return
     */
    @GetMapping("/clear")
    public CommonResult clearCart(@RequestParam(value = "cartKey", required = false) String cartKey,
                                  @RequestParam(value = "accessToken",required = false) String accessToken){

        CartResponse cartResponse = cartService.clearCart(cartKey,accessToken);
        return new CommonResult().success(cartResponse);
    }

    @ApiOperation("更新购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuIds", value = "选中的商品的skuId，多个skuId用','隔开"),
            @ApiImplicitParam(name = "ops", value = "操作，选中：1，未选中：0",defaultValue ="1"),
            @ApiImplicitParam(name = "cartKey", value = "离线购物车key,可以没有"),
            @ApiImplicitParam(name = "accessToken", value = "登陆后的访问令牌，没登录不用传"),
    })
    @PostMapping("/check")
    public CommonResult checkCart(@NotNull @RequestParam("skuIds") String skuIds,
                                  @NotNull @RequestParam(value = "ops",defaultValue ="1") Integer ops,
                                  @RequestParam(value = "cartKey", required = false) String cartKey,
                                  @RequestParam(value = "accessToken", required = false) String accessToken){

        CartResponse cartResponse = cartService.checkCartItems(skuIds,ops,cartKey,accessToken);
        return new CommonResult().success(cartResponse);
    }

}
