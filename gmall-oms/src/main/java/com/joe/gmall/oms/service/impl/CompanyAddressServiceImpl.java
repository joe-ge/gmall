package com.joe.gmall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joe.gmall.oms.entity.CompanyAddress;
import com.joe.gmall.oms.mapper.CompanyAddressMapper;
import com.joe.gmall.oms.service.CompanyAddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司收发货地址表 服务实现类
 * </p>
 *
 * @author Joe
 * @since 2020-01-05
 */
@Service
public class CompanyAddressServiceImpl extends ServiceImpl<CompanyAddressMapper, CompanyAddress> implements CompanyAddressService {

}
