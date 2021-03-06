package com.zrzhao.mysql.configrution;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
@Aspect
@Order(-2)
@Component
public class DynamicDataSourceAspect {

    @Pointcut("execution(public * com.zrzhao.mysql.order.service..*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void changeDataSource(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CustomDataSource annotation = method.getAnnotation(CustomDataSource.class);
        if (annotation != null) {
            return;
        }
        if (method.getName().startsWith("read")) {
            DynamicDataSourceContextHolder.setDataSourceKey("read");
        } else {
            DynamicDataSourceContextHolder.setDataSourceKey("write");
        }
    }

    @After("pointCut()")
    public void restoreDataSource(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CustomDataSource annotation = method.getAnnotation(CustomDataSource.class);
        if (annotation != null) {
            return;
        }
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

}
