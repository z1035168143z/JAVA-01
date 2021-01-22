package com.zrzhao.gateway.client;

/**
 * 访问客户端
 *
 * @author zrzhao
 * @date 2021/1/18
 */
public class RequestClientDemo {

    public static void main(String[] args) {
        // 单线程处理
        String url = "http://localhost:8801";
        String result = HttpClientUtil.getInstance().requestGet(url);
        System.out.println(result);

        // 每次都new线程处理
        url = "http://localhost:8802";
        result = HttpClientUtil.getInstance().requestGet(url);
        System.out.println(result);

        // 线程池处理
        url = "http://localhost:8803";
        result = HttpClientUtil.getInstance().requestGet(url);
        System.out.println(result);

        // netty
        url = "http://localhost:8808/test";
        result = HttpClientUtil.getInstance().requestGet(url);
        System.out.println(result);
    }


}
