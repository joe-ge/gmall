package com.joe.gmall.admin.aop;

import com.joe.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @program: gmail
 * @description 利用aop完成统一的数据校验
 *
 * 如何编写切面
 *  1，导入切面场景
 *  2，编写切面
 *      1）@Aspect @Component
 *      2)切入点表达式
 *      3）通知
 *          前置通知：方法执行之前触发
 *          后置通知：方法执行之后触发
 *          返回通知：方法正常返回之后触发
 *          异常通知：方法出现异常触发
*         正常执行：前置通知==>返回通知==>后置通知
*         异常执行：前置通知==>异常通知==>后置通知
 *          环绕通知：四合一，可以拦截方法的执行
 *
 * @author: Joe
 * @create: 2020-01-06
 */
@Slf4j
@Aspect
@Component
public class DataValidAspect {

    /**
     * 目标方法的异常一般都需要再次抛出去，让别人感知
     * @param point
     * @return
     */
    @Around("execution(* com.joe.gmall..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint point) throws Throwable{
        Object proceed = null;
        CommonResult commonResult = new CommonResult();
        //log.debug("开始前置通知,校验切面介入工作...");
        Object[] args = point.getArgs();
        for (Object arg: args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.getErrorCount() > 0) {
                    log.debug("校验切面发现校验错误:{}",bindingResult);
                    return commonResult.validateFailed(bindingResult);
                }
            }
        }
        //方法的正常执行 相当于method.invoke();
        proceed = point.proceed(args);
        //返回通知 log.debug("返回通知，校验正常已将方法放行...");
        //正常返回一定要是point.proceed(args)的执行结果，原封不动
        return proceed;
    }
}