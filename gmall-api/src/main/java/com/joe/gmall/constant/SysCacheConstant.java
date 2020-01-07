package com.joe.gmall.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: gmail
 * @description 系统中缓存使用的常量
 * @author: Joe
 * @create: 2020-01-07
 */
@ApiModel(description = "系统中缓存使用的常量")
public class SysCacheConstant {
    @ApiModelProperty("菜单缓存NAMESPACE")
    public static final String MENU_CACHE_NAME = "menu_namespace";
    @ApiModelProperty("三级菜单缓存key名")
    public static final String CATEGORY_MENU_CACHE_KEY = "'sys_category_pid'+";

}
