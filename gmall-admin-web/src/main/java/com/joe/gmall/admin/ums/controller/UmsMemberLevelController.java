package com.joe.gmall.admin.ums.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.joe.gmall.to.CommonResult;
import com.joe.gmall.ums.entity.MemberLevel;
import com.joe.gmall.ums.service.MemberLevelService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: gmail
 * @description
 * @author: Joe
 * @create: 2020-01-07
 */
@CrossOrigin
@RestController
@RequestMapping("/memberLevel")
public class UmsMemberLevelController {

    @Reference(version = "1.0")
    MemberLevelService memberLevelService;

    @GetMapping("/list")
    public Object getMemberLevel(Integer defaultStatus) {
        //先默认查所有会员，default_status不用
        List<MemberLevel> list = memberLevelService.list();
        return new CommonResult().success(list);
    }
}
