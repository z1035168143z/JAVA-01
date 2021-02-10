package com.zrzhao.demo.utils;

/**
 * @author zrzhao
 * @date 2021/2/9
 */
public class JsonResult<T> implements java.io.Serializable {

    private int code;
    private T data;
    private String msg;

    private JsonResult() {

    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();

        jsonResult.code = 0;
        jsonResult.data = data;

        return jsonResult;
    }

    public static <T> JsonResult<T> fail(String msg) {
        JsonResult<T> jsonResult = new JsonResult<>();

        jsonResult.code = 1;
        jsonResult.msg = msg;

        return jsonResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
