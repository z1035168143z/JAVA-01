package io.zrzhao.server.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * http请求 创建枚举单例
 *
 * @author zrzhao
 * @date 2021/1/18
 */
public class HttpClientUtil {

    private static HttpClientUtil httpClientUtil;
    private static HttpClientBuilder builder;

    private HttpClientUtil() {

    }

    public static HttpClientUtil getInstance() {
        if (httpClientUtil != null) {
            return httpClientUtil;
        }
        synchronized (HttpClientUtil.class) {
            if (httpClientUtil != null) {
                return httpClientUtil;
            }
            PoolingHttpClientConnectionManager poolConnectionManager = new PoolingHttpClientConnectionManager();
            builder = HttpClients.custom().setConnectionManager(poolConnectionManager);
            poolConnectionManager.setDefaultMaxPerRoute(25);
            poolConnectionManager.setMaxTotal(50);
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(25000)
                    .setConnectionRequestTimeout(25000)
                    .setSocketTimeout(25000)
                    .build();
            builder.setDefaultRequestConfig(defaultRequestConfig);
            httpClientUtil = new HttpClientUtil();
        }
        return httpClientUtil;
    }

    public String requestGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return doRequest(httpGet);
    }

    public String requestPost(String url, String content, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
        if (content != null && content.trim().length() > 0) {
            httpPost.setEntity(new StringEntity(content, Charset.defaultCharset()));
        }
        return doRequest(httpPost);
    }

    private String doRequest(HttpRequestBase requestBase) {
        CloseableHttpClient httpClient = builder.build();
        try (CloseableHttpResponse execute = httpClient.execute(requestBase);) {
            return EntityUtils.toString(execute.getEntity(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
