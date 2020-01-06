package com.joe.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.ums.entity.Admin;
import com.joe.gmall.ums.mapper.AdminMapper;
import com.joe.gmall.ums.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Component
@Service(version = "1.0")
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin login(String username, String password) {

        String passwdmd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>().eq("username", username).eq("password", passwdmd5);
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }


    @Override
    public Admin getUserInfo(String userName) {
        return baseMapper.selectOne(new QueryWrapper<Admin>().eq("username",userName));
    }
}
