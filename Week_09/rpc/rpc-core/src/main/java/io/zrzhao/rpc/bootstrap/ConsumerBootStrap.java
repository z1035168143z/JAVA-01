package io.zrzhao.rpc.bootstrap;

import io.zrzhao.rpc.annotation.Consumer;
import io.zrzhao.rpc.connection.ConsumerConnection;
import io.zrzhao.rpc.handler.RpcConsumerInvokeHandler;
import io.zrzhao.rpc.pojo.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zrzhao
 * @date 2021/2/11
 */
@Component
@Slf4j
@ConditionalOnBean(name = "rpcConsumerApplication")
public class ConsumerBootStrap implements InstantiationAwareBeanPostProcessor {

    private final RpcContext rpcContext = RpcContext.getContext();

    private final String scanPackage = "io.zrzhao.rpc";

    private final ConsumerConnection consumerConnection = new ConsumerConnection();

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (!bean.getClass().getPackage().getName().startsWith(scanPackage)) {
            return null;
        }
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        List<Field> consumerReferences = Arrays.stream(declaredFields).filter(field -> field.isAnnotationPresent(Consumer.class)).collect(Collectors.toList());

        consumerReferences.forEach(consumerReference -> {
            Object consumer = createConsumer(consumerReference.getType());
            try {
                consumerReference.setAccessible(true);
                consumerReference.set(bean, consumer);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        });
        return null;
    }

    private Object createConsumer(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new RpcConsumerInvokeHandler(consumerConnection));
    }

}
