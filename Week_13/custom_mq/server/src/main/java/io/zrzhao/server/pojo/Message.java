package io.zrzhao.server.pojo;

import java.util.Map;

/**
 * @author zrzhao
 * @date 2021/4/17
 */
public class Message {

    private Map<String, Object> header;

    private Object body;

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
