package com.joe.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.SkuStock;
import com.joe.gmall.pms.mapper.SkuStockMapper;
import com.joe.gmall.pms.service.SkuStockService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {

}
