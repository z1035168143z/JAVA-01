package io.zrzhao.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author zrzhao
 * @date 2021/5/9
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Consumer {
}
