package com.joe.gmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: gmail
 * @description
 * @author: Joe
 * @create: 2020-01-06 08:59
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页查询返回对象")
@Data
public class PageInfoVo implements Serializable {

    @ApiModelProperty("总记录数")
    private Long total;

    @ApiModelProperty("总页码")
    private Long totalPages;

    @ApiModelProperty("每页显示的记录数")
    private Long pageSize;

    @ApiModelProperty("每页查出的数据")
    private List<? extends Object> list;

    @ApiModelProperty("当前页码")
    private Long pageNum;


}
