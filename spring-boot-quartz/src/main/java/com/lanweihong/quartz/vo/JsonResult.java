package com.lanweihong.quartz.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 封装统一的返回数据格式
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:33
 */
@Getter
@Setter
@Accessors(chain = true)
public class JsonResult<T> {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    private boolean success;
    private String message;
    private String error;
    private T data;

    public static <T> JsonResult<T> ok() {
        return new JsonResult<T>()
                .setData(null)
                .setSuccess(true)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> JsonResult<T> ok(String message) {
        return new JsonResult<T>()
                .setData(null)
                .setSuccess(true)
                .setMessage(message);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<T>()
                .setData(data)
                .setSuccess(true)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static JsonResult<String> error(String error) {
        return new JsonResult<String>()
                .setData(null)
                .setSuccess(false)
                .setError(error);
    }


}
