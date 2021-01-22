package com.zrzhao.gateway.router;

import java.util.List;

/**
 * 路由
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public interface HttpEndpointRouter {

    /**
     * choose server
     *
     * @param urls
     * @return
     */
    String route(List<String> urls);

}
