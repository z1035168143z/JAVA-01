package io.zrzhao.server.api.vo;

import lombok.Data;

/**
 * 包装返回结果
 *
 * @author zrzhao
 * @date 2021/4/17
 */
@Data
public class Result<T> implements java.io.Serializable {

    private int code;
    private T data;
    private String msg;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 0;
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.data = data;
        return result;
    }

    public static <T> Result<T> failure() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }

}
