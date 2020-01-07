package com.joe.gmall.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @program: gmail
 * @description
 * @author: Joe
 * @create: 2020-01-07
 */
public class PageUtils {
    public static<T> Page<T> construct(Class<T> classType, Long pageNumber, Long pageSize){
        return new Page<T>(pageNumber,pageSize);
    }
    public static<T> Page<T> construct(Class<T> classType , Integer pageNumber, Integer pageSize){
        return new Page<T>(pageNumber.longValue(),pageSize.longValue());
    }
}
