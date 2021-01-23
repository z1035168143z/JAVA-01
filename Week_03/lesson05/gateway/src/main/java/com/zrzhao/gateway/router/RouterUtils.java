package com.zrzhao.gateway.router;

/**
 * @author zrzhao
 * @date 2021/1/23
 */
public class RouterUtils {

    private HttpEndpointRouter router;

    private static class RouterUtilsHolder {
        private static final RouterUtils ROUTER_UTILS = new RouterUtils();
    }

    public static RouterUtils getInstance() {
        return RouterUtilsHolder.ROUTER_UTILS;
    }

    private RouterUtils() {
        this.router = new RandomHttpEndpointRouter();
    }

    public HttpEndpointRouter getRouter() {
        return this.router;
    }

    public void register(HttpEndpointRouter router) {
        this.router = router;
    }


}
