package com.zrzhao.mysql.configrution;

import java.lang.annotation.*;

/**
 * @author zrzhao
 * @date 2021/3/6
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomDataSource {

    String value() default "";
}
