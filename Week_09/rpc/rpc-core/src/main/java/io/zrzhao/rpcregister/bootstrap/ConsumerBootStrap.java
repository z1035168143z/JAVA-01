package io.zrzhao.rpcregister.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.zrzhao.rpcregister.annotation.Consumer;
import io.zrzhao.rpcregister.connection.ConsumerConnection;
import io.zrzhao.rpcregister.handler.RpcConsumerInvokeHandler;
import io.zrzhao.rpcregister.pojo.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author zrzhao
 * @date 2021/2/11
 */
@Component
@Slf4j
public class ConsumerBootStrap implements InstantiationAwareBeanPostProcessor {

    private final RpcContext rpcContext = RpcContext.getContext();

    private final String scanPackage = this.getClass().getPackage().getName();

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
