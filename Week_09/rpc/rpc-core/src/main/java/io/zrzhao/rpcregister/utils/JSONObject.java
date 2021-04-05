package io.zrzhao.rpcregister.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * @author zrzhao
 * @date 2021/3/27
 */
public class JSONObject {

    private static final JsonMapper jsonMapper = new JsonMapper();

    public static String toJsonString(Object obj) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(obj);
    }

    public static <T> T parseObject(String obj, Class<T> clazz) {
        return jsonMapper.convertValue(obj, clazz);
    }

}
