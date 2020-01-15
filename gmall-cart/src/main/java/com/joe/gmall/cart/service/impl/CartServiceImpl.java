package com.joe.gmall.cart.service.impl;
import	java.util.ArrayList;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.joe.gmall.cart.component.MemberComponent;
import com.joe.gmall.cart.service.CartService;
import com.joe.gmall.cart.vo.Cart;
import com.joe.gmall.cart.vo.CartItemVo;
import com.joe.gmall.cart.vo.CartResponse;
import com.joe.gmall.cart.vo.UserCartKey;
import com.joe.gmall.constant.CartConstant;
import com.joe.gmall.constant.SysCacheConstant;
import com.joe.gmall.oms.entity.CartItem;
import com.joe.gmall.pms.entity.Product;
import com.joe.gmall.pms.entity.SkuStock;
import com.joe.gmall.pms.service.ProductService;
import com.joe.gmall.pms.service.SkuStockService;
import com.joe.gmall.ums.entity.Member;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: gmall
 * @description
 * @author: Joe
 * @create: 2020-01-14
 */
@Service(version = "1.0")
@Component
public class CartServiceImpl implements CartService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    MemberComponent memberComponent;

    @Qualifier("mainThreadPoolExecutor")
    @Autowired
    ThreadPoolExecutor mainThreadPoolExecutor;

    @Reference(version = "1.0")
    SkuStockService skuStockService;

    @Reference(version = "1.0")
    ProductService productService;

    @Override
    public CartResponse addToCart(Long skuId, Integer count, String cartKey, String accessToken) throws ExecutionException, InterruptedException {
        CartResponse cartResponse = new CartResponse();
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        //合并购物车
        if (userCartKey.isLogin() && cartKey != null) {
            mergeCart(cartKey, userCartKey.getUserId());
        }
        //有用户信息，即在线购物车
        //没有，有cartKey，离线购物车
        //都没有，刚进来，添加cartKey，生成离线购物车

        CartItemVo cartItemVo = addItemToCart(skuId,count, userCartKey.getFinalCartKey());
        cartResponse.setCartItemVo(cartItemVo);
        //设置临时购物车用的cartKey
        cartResponse.setCartKey(userCartKey.getTempCartKey());
        CartResponse listCart = listCart(cartKey, accessToken);
        cartResponse.setCart(listCart.getCart());
        return cartResponse;
    }

    @Override
    public CartResponse updateCartItemNum(Long skuId, Integer count, String cartKey, String accessToken) throws ExecutionException, InterruptedException {
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        RMap<String, String> map = redissonClient.getMap(userCartKey.getFinalCartKey());
        map.expire(CartConstant.CART_KEY_EXPIRE, TimeUnit.DAYS);

        String jsonItem = map.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(jsonItem, CartItemVo.class);
        cartItemVo.setCount(count);

        String jsonString = JSON.toJSONString(cartItemVo);
        map.put(skuId.toString(), jsonString);

        CartResponse cartResponse = new CartResponse();
        CartResponse listCart = listCart(cartKey, accessToken);
        cartResponse.setCart(listCart.getCart());
        cartResponse.setCartItemVo(cartItemVo);
        return cartResponse;
    }

    @Override
    public CartResponse listCart(String cartKey, String accessToken) throws ExecutionException, InterruptedException {
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        if (userCartKey.isLogin()) {
            //用户登陆了需要合并购物车
            mergeCart(cartKey, userCartKey.getUserId());
        }

        RMap<String, String> map = redissonClient.getMap(userCartKey.getFinalCartKey());
        Cart cart = new Cart();
        List<CartItemVo> cartItemVoList = new ArrayList<> ();
        if (map != null && !map.isEmpty()) {
            map.entrySet().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                CartItemVo cartItemVo = JSON.parseObject(value, CartItemVo.class);
                cartItemVoList.add(cartItemVo);
            });
        }
        cart.setCartItemVos(cartItemVoList);
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCart(cart);
        return cartResponse;
    }

    @Override
    public CartResponse deleteCart(Long skuId, String cartKey, String accessToken) throws ExecutionException, InterruptedException {
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        RMap<String, String> map = redissonClient.getMap(userCartKey.getFinalCartKey());
        map.expire(CartConstant.CART_KEY_EXPIRE, TimeUnit.DAYS);
        map.remove(skuId.toString());

        CartResponse cartResponse = new CartResponse();
        CartResponse listCart = listCart(cartKey, accessToken);
        cartResponse.setCart(listCart.getCart());
        return cartResponse;
    }

    @Override
    public CartResponse clearCart(String cartKey, String accessToken) {
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        RMap<String, String> map = redissonClient.getMap(userCartKey.getFinalCartKey());
        map.clear();
        CartResponse cartResponse = new CartResponse();
        return cartResponse;
    }

    @Override
    public CartResponse checkCartItems(String skuIds, Integer ops, String cartKey, String accessToken) {
        UserCartKey userCartKey = memberComponent.getCartKey(accessToken, cartKey);
        RMap<String, String> map = redissonClient.getMap(userCartKey.getFinalCartKey());
        map.expire(CartConstant.CART_KEY_EXPIRE, TimeUnit.DAYS);
        boolean check = ops == 1 ? true : false;
        if (!StringUtils.isEmpty(skuIds)) {
            String[] skuIdArr = skuIds.split(",");
            for (String skuId : skuIdArr) {
                String jsonItem = map.get(skuId);
                if (jsonItem != null) {
                    CartItemVo itemVo = JSON.parseObject(jsonItem, CartItemVo.class);
                    itemVo.setCheck(check);
                    map.put(skuId, JSON.toJSONString(itemVo));
                }
            }
        }
        return null;
    }

    private void mergeCart(String cartKey, Long userId) throws ExecutionException, InterruptedException {
        RMap<String, String> map = redissonClient.getMap(CartConstant.TEMP_CART_KEY_PREFIX + cartKey);
        if(map != null && !map.isEmpty()){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                CartItemVo cartItemVo = JSON.parseObject(value, CartItemVo.class);
                addItemToCart(Long.valueOf(key), cartItemVo.getCount(), CartConstant.USER_CART_KEY_PREFIX + userId);
            }
            map.clear();
        }
    }

    /**
     * 添加商品到购物车
     * 1.按照skuId找到真正的sku信息
     * 2.给指定的购物车添加记录
     * 如果已经有了这个skuId只是count增加
     *
     * @param skuId
     * @param finallCartKey
     * @return
     */
    private CartItemVo addItemToCart(Long skuId,Integer count, String finallCartKey) throws ExecutionException, InterruptedException {
        CartItemVo newCartItemVo = new CartItemVo();

        //查询耗时，开启带返回结果的异步线程
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            //查询对应的sku信息
            SkuStock skuStock = skuStockService.getById(skuId);
            return skuStock;
        }, mainThreadPoolExecutor).thenAcceptAsync((stock) -> {
            String productName = "";
            if (stock != null) {
                Product product = productService.getById(stock.getProductId());
                if (product != null) {
                    productName = product.getName();
                }
            }
            BeanUtils.copyProperties(stock, newCartItemVo);
            newCartItemVo.setSkuId(stock.getId());
            newCartItemVo.setName(productName);
            newCartItemVo.setCount(count);
        });

        /**
         * 购物车集合 k[skuId]是str,v[购物项]是str(json)
         * k[checked] v[1,2]
         */
        RMap<String, String> map = redissonClient.getMap(finallCartKey);
        map.expire(CartConstant.CART_KEY_EXPIRE, TimeUnit.DAYS);
        //获取这个购物车中这个skuid对应的购物项
        String itemJson = map.get(skuId.toString());
        //检查购物车中是否以已经存在这个购物项
        future.get();//在线等结果
        if (!StringUtils.isEmpty(itemJson)) {
            //只是数量增加
            CartItemVo oldCartItemVo = JSON.parseObject(itemJson, CartItemVo.class);
            Integer oldCount = oldCartItemVo.getCount();
            //异步任务完成，newCartItemVo才可以用
            newCartItemVo.setCount(oldCount + newCartItemVo.getCount());
            String jsonString = JSON.toJSONString(newCartItemVo);
            //老数据覆盖成新数据
            map.put(skuId.toString(), jsonString);

        } else {
            //新添加
            //异步任务完成，newCartItemVo才可以用
            String jsonString = JSON.toJSONString(newCartItemVo);
            map.put(skuId.toString(), jsonString);
        }
        return newCartItemVo;
    }
}
