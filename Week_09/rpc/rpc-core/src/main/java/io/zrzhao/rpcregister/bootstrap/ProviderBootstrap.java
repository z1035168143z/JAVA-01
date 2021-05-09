package io.zrzhao.rpcregister.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.zrzhao.rpcregister.annotation.Provider;
import io.zrzhao.rpcregister.handler.ProviderRegisterHandler;
import io.zrzhao.rpcregister.pojo.ProviderMethod;
import io.zrzhao.rpcregister.pojo.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * 注册provider
 *
 * @author zrzhao
 * @date 2021/2/10
 */
@Slf4j
@Component
public class ProviderBootstrap {

    private final RpcContext rpcContext = new RpcContext();

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${rpc.provider.port}")
    private int serverPort;

    @PostConstruct
    public void start() throws InterruptedException {
        registerProvider();

        startProviderServer();
    }

    /**
     * 扫描provider，存储在 rpcContext
     */
    private void registerProvider() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            Object bean = applicationContext.getBean(beanDefinitionNames[i]);
            Provider provider = bean.getClass().getAnnotation(Provider.class);
            if (provider == null) {
                continue;
            }
            // 获取 bean 所实现的所有接口
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces == null || interfaces.length == 0) {
                continue;
            }

            // 存储 provider method
            MultiValueMap<String, ProviderMethod> providerMethodStore = rpcContext.getProviderMethodStore();
            for (Class<?> anInterface : interfaces) {
                for (Method method : anInterface.getMethods()) {
                    providerMethodStore.add(anInterface.getName(), ProviderMethod.create(method, bean));
                }
            }
        }
    }

    private void startProviderServer() {
        ProviderRegisterHandler registerHandler = new ProviderRegisterHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel c) throws Exception {
                        c.pipeline().addLast(registerHandler);
                    }
                });
        try {
            serverBootstrap.bind(serverPort).sync();
        } catch (Exception e) {
            log.error("provider server boot error", e);
            throw new RuntimeException(e);
        }
    }


}
