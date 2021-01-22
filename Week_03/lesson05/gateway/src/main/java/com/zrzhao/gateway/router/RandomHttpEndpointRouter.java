package com.zrzhao.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * 随机访问
 *
 * @author zrzhao
 * @date 2021/1/22
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> urls) {
        return urls.get(new Random().nextInt(urls.size()));
    }
}
