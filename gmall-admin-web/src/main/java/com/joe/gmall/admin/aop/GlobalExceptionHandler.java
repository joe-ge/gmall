package com.joe.gmall.admin.aop;

import com.joe.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: gmail
 * @description 统一处理所有异常,返回状态500的json  @ControllerAdvice表示该类是一个异常处理类
 *一定要注意再有环绕通知切面的时候，确定该切面将异常再次抛出
 * @author: Joe
 * @create: 2020-01-06
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public Object globalExceptionHandler(Exception e) {
        log.error("系统全局异常：{}",e.getMessage());
        e.printStackTrace();
        return new CommonResult().failed();
    }
}
