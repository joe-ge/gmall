package com.joe.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.pms.entity.*;
import com.joe.gmall.pms.mapper.*;
import com.joe.gmall.pms.service.ProductService;
import com.joe.gmall.vo.PageInfoVo;
import com.joe.gmall.vo.product.PmsProductParam;
import com.joe.gmall.vo.product.PmsProductQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Slf4j
@Component
@Service(version = "1.0")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductAttributeValueMapper productAttributeValueMapper;

    @Autowired
    ProductFullReductionMapper productFullReductionMapper;

    @Autowired
    ProductLadderMapper productLadderMapper;

    @Autowired
    SkuStockMapper skuStockMapper;

    private ThreadLocal<Long> productId = new ThreadLocal<Long>();


    @Override

//    @Transactional(propagation = Propagation.REQUIRED,
//            rollbackFor = Exception.class ,/*By default, a transaction will be rolling back on {@link RuntimeException}
//                                             and {@link Error} but not on checked exceptions (business exceptions)
//                                             公司一般都指定为Exception.class */
//            noRollbackFor = FileNotFoundException.class /*指定可回滚中的不回滚情景 */
//    )
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveProduct(PmsProductParam productParam) {
        boolean result = true;

        /*
        直接调用在本类中的方法会在编译时就把方法体整合在一起，达不到想要的不同方法事务分离的效果。
        Spring提供了aop代理对象暴露，可以在本类中获取自己的代理对象，达到对象.方法()的形式以实现真正的事务隔离结果。
        */
        ProductServiceImpl proxy = (ProductServiceImpl) AopContext.currentProxy();

        //确保前两个方法必须保存成功，若失败就整个方法回滚
        //pms_product：基本信息
        proxy.saveProductBaseInfo(productParam);


        //pms_sku_stock:sku库存表
        proxy.saveSkuStock(productParam);

        log.debug("商品的自增id:{}", productId.get());

        //后三个方法开新车，出错确保不影响前面的执行结果，他们三个之间也互不影响
        //pms_product_attribute_value:所有属性的值
        try {
            proxy.saveProductAttributeValue(productParam);
        } catch (Exception e) {//try catch 不throw 的目的只是为了出错也不会影响后面的方法执行，即使不加也不会影响前面两个执行成功的方法
            result = false;
            log.error("商品信息注册时saveProductAttributeValue失败:{}",e.getMessage());
            e.printStackTrace();
        }

        //pms_product_full_reduction:满减信息
        try {
            proxy.savePoductFullReduction(productParam);
        } catch (Exception e) {
            result = false;
            log.error("商品信息注册时savePoductFullReduction失败:{}",e.getMessage());
            e.printStackTrace();
        }

        //pms_product_ladder:满减表
        try {
            proxy.saveProductLadder(productParam);
        } catch (Exception e) {
            result = false;
            log.error("商品信息注册时saveProductAttributeValue失败:{}",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveSkuStock(PmsProductParam productParam) {
        List<SkuStock> skuStockList = productParam.getSkuStockList();
        for (int i = 1; i <= skuStockList.size(); i++) {
            SkuStock skuStock = skuStockList.get(i - 1);
            //skuCode必须有,生成规则 pid_sku自增id
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                skuStock.setSkuCode(productId.get() + "_" + i);
            }
            skuStock.setProductId(productId.get());
            skuStockMapper.insert(skuStock);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductLadder(PmsProductParam productParam) {
        List<ProductLadder> productLadderList = productParam.getProductLadderList();
        productLadderList.forEach(productLadder -> {
            productLadder.setProductId(productId.get());
            productLadderMapper.insert(productLadder);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savePoductFullReduction(PmsProductParam productParam) {
        List<ProductFullReduction> productFullReductionList = productParam.getProductFullReductionList();
        productFullReductionList.forEach(productFullReduction -> {
            productFullReduction.setProductId(productId.get());
            productFullReductionMapper.insert(productFullReduction);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProductAttributeValue(PmsProductParam productParam) {
        List<ProductAttributeValue> productAttributeValueList = productParam.getProductAttributeValueList();
        productAttributeValueList.forEach(productAttributeValue -> {
            productAttributeValue.setProductId(productId.get());
            productAttributeValueMapper.insert(productAttributeValue);
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveProductBaseInfo(PmsProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        baseMapper.insert(product);
        //mybatisplus可以自动获取自增id
        productId.set(product.getId());
    }

    @Override
    public PageInfoVo productPageInfo(PmsProductQueryParam param) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<Product>()
                .eq("delete_status", new Integer(0));
        if (!StringUtils.isEmpty(param.getKeyword())) {
            queryWrapper.like("name", param.getKeyword());
        }
        if (param.getBrandId() != null) {
            queryWrapper.eq("brand_id", param.getBrandId());
        }
        if (param.getProductCategoryId() != null) {
            queryWrapper.eq("product_category_id", param.getProductCategoryId());
        }
        if (!StringUtils.isEmpty(param.getProductSn())) {
            queryWrapper.like("product_sn" , param.getProductSn());
        }
        if (param.getPublishStatus() != null) {
            queryWrapper.eq("publish_status" , param.getPublishStatus());
        }
        if (param.getVerifyStatus() != null) {
            queryWrapper.eq("verify_status", param.getVerifyStatus());
        }
        IPage<Product> page = baseMapper.selectPage(new Page<Product>(param.getPageNum(), param.getPageSize()), queryWrapper);
        PageInfoVo pageInfoVo = new PageInfoVo(page.getTotal(), page.getPages(), param.getPageSize(), page.getRecords(), page.getCurrent());
        return pageInfoVo;
    }
}
