package com.lanweihong.springdoc.swagger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装统一的返回数据格式
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:33
 */
@Getter
@Setter
@Schema(description = "通用响应数据")
public class JsonResult<T> {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    @Schema(description = "成功状态：true 成功，false 失败")
    private boolean success;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "错误信息")
    private String error;

    @Schema(description = "数据主体")
    private T data;

    public JsonResult() {

    }

    public JsonResult(boolean success, String message, T data) {
        this(success, message, null, data);
    }

    public JsonResult(boolean success, String error) {
        this(false, null, error, null);
    }

    public JsonResult(T data) {
        this(true, DEFAULT_SUCCESS_MESSAGE, null, data);
    }

    public JsonResult(boolean success, String message, String error, T data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    public static <T> JsonResult<T> ok() {
        return new JsonResult<>(true, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public static <T> JsonResult<T> ok(String message) {
        return new JsonResult<>(true, message, null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<>(true, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static JsonResult<String> error(String error) {
        return new JsonResult<>(false, null, error, null);
    }

}
