package com.zrzhao.mysql.configrution;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAnnotationAspect {

    @Before("@annotation(customDataSource)")
    public void changeDataSource(JoinPoint point, CustomDataSource customDataSource) throws Throwable {
        String value = customDataSource.value();
        if (StringUtils.hasLength(value)) {
            DynamicDataSourceContextHolder.setDataSourceKey(value);
        }
    }

    @After("@annotation(customDataSource)")
    public void restoreDataSource(JoinPoint point, CustomDataSource customDataSource) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

}
