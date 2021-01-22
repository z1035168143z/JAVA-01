package com.zrzhao.gateway;

import com.zrzhao.gateway.filter.chain.HttpRequestFilterChain;
import com.zrzhao.gateway.filter.chain.HttpResponseFilterChain;
import com.zrzhao.gateway.inbound.HttpInboundHandler;
import com.zrzhao.gateway.outbound.HttpOutboundHandler;
import com.zrzhao.gateway.router.HttpEndpointRouter;
import com.zrzhao.gateway.router.RandomHttpEndpointRouter;
import com.zrzhao.gateway.server.HttpBoundInitializer;
import com.zrzhao.gateway.server.NettyHttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Netty启动主类
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class NettyServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerApplication.class);

    private static final Map<String, HttpEndpointRouter> ROUTER_MAP = new HashMap<>((int) (1 / .75f) + 1);

    static {
        ROUTER_MAP.put("random", new RandomHttpEndpointRouter());
    }

    public static void main(String[] args) {
        // 端口
        int port = Integer.parseInt(System.getProperty("port", "8888"));

        HttpRequestFilterChain httpRequestFilterChain = new HttpRequestFilterChain();

        HttpResponseFilterChain httpResponseFilterChain = new HttpResponseFilterChain();
        NettyHttpServer nettyHttpServer = new NettyHttpServer(port, new HttpBoundInitializer(httpRequestFilterChain, httpResponseFilterChain));
        try {
            nettyHttpServer.run();
        } catch (InterruptedException e) {
            logger.info("netty服务运行出错", e);
        }
    }

    private static HttpEndpointRouter chooseHttpEndpointRouter(String routerType) {
        return ROUTER_MAP.get(routerType);
    }

}
