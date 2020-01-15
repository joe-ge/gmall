package com.joe.gmall.cart.service;

import com.joe.gmall.cart.vo.CartResponse;

import java.util.concurrent.ExecutionException;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
public interface CartService {
    CartResponse addToCart(Long skuId, Integer count, String cartKey, String accessToken) throws ExecutionException, InterruptedException;

    CartResponse updateCartItemNum(Long skuId, Integer count, String cartKey, String accessToken) throws ExecutionException, InterruptedException;

    CartResponse listCart(String cartKey, String accessToken) throws ExecutionException, InterruptedException;

    CartResponse deleteCart(Long skuId, String cartKey, String accessToken) throws ExecutionException, InterruptedException;

    CartResponse clearCart(String cartKey, String accessToken);

    CartResponse checkCartItems(String skuIds, Integer ops, String cartKey, String accessToken);
}
