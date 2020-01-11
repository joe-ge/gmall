package com.joe.gmall.admin.pms.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.joe.gmall.vo.product.PmsProductParam;
import com.joe.gmall.vo.product.PmsProductQueryParam;
import com.joe.gmall.pms.service.ProductService;
import com.joe.gmall.to.CommonResult;
import com.joe.gmall.vo.PageInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 */
@Slf4j
@CrossOrigin
@RestController
@Api(tags = "PmsProductController", description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {
    @Reference(version = "1.0" ,parameters = {
            "saveProduct.timeout","3000",
            "saveProduct.retries","0",
            "updatePublishStatus.timeout","5000",
            "updatePublishStatus.retries","0"})
    private ProductService productService;

    @ApiOperation("创建商品")
    @PostMapping(value = "/create")
    public Object create(@RequestBody PmsProductParam productParam,
                         BindingResult bindingResult) {
        //TODO 查询所有一级分类及子分类
        log.debug("将要保存的商品信息：{}",productParam);
        boolean result = productService.saveProduct(productParam);
        return new CommonResult().success(result);
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/updateInfo/{id}")
    public Object getUpdateInfo(@PathVariable Long id) {
        //TODO 根据商品id获取商品编辑信息
        return new CommonResult().success(null);
    }

    @ApiOperation("更新商品")
    @PostMapping(value = "/update/{id}")
    public Object update(@PathVariable Long id, @RequestBody PmsProductParam productParam, BindingResult bindingResult) {
        //TODO 更新商品
        return new CommonResult().success(null);
    }

    @ApiOperation("查询商品")
    @GetMapping(value = "/list")
//    public Object getList(PmsProductQueryParam productQueryParam,
//                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
//                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    public Object getList(PmsProductQueryParam productQueryParam) {
        //TODO 查询商品
        PageInfoVo pageInfoVo = productService.productPageInfo(productQueryParam);
        return new CommonResult().success(pageInfoVo);
    }

    @ApiOperation("根据商品名称或货号模糊查询")
    @GetMapping(value = "/simpleList")
    public Object getList(String  keyword) {
        //TODO 根据商品名称或货号模糊查询
        return new CommonResult().success(null);
    }

    @ApiOperation("批量修改审核状态")
    @PostMapping(value = "/update/verifyStatus")
    public Object updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("verifyStatus") Integer verifyStatus,
                                     @RequestParam("detail") String detail) {
        //TODO 批量修改审核状态
        return new CommonResult().success(null);
    }

    @ApiOperation("批量上下架")
    @PostMapping(value = "/update/publishStatus")
    public Object updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                     @RequestParam("publishStatus") Integer publishStatus) {
        //TODO 批量上下架
        productService.updatePublishStatus(ids, publishStatus);
        return new CommonResult().success(null);
    }

    @ApiOperation("批量推荐商品")
    @PostMapping(value = "/update/recommendStatus")
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                      @RequestParam("recommendStatus") Integer recommendStatus) {
        //TODO 批量推荐商品
        return new CommonResult().success(null);
    }

    @ApiOperation("批量设为新品")
    @PostMapping(value = "/update/newStatus")
    public Object updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        //TODO 批量设为新品
        return new CommonResult().success(null);
    }

    @ApiOperation("批量修改删除状态")
    @PostMapping(value = "/update/deleteStatus")
    public Object updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                  @RequestParam("deleteStatus") Integer deleteStatus) {
        //TODO 根据商品id获取商品编辑信息
        return new CommonResult().success(null);
    }
}
