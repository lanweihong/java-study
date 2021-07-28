package com.lanweihong.common.base.vo;

import com.lanweihong.common.base.enums.MessageEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:06
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 6046499770215249469L;

    /**
     * 默认成功码
     */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    /**
     * 默认错误码
     */
    public static final int DEFAULT_ERROR_CODE = -1;

    /**
     * 默认成功信息
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";


    /**
     * 状态
     */
    private boolean success;

    /**
     * 错误码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 错误消息
     */
    private String error;

    /**
     * 数据
     */
    private T data;

    private JsonResult (boolean success, int code, String message, String error, T object) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.error = error;
        this.data = object;
    }

    public static <T> JsonResult<T> ok() {
        return JsonResult.<T>Builder()
                .status(true)
                .code(DEFAULT_SUCCESS_CODE)
                .message(DEFAULT_SUCCESS_MESSAGE)
                .data(null)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> ok(MessageEnum messageEnum) {
        return JsonResult.<T>Builder()
                .status(true)
                .code(messageEnum.getCode())
                .message(messageEnum.getMessage())
                .data(null)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> ok(T data) {
        return JsonResult.<T>Builder()
                .status(true)
                .code(DEFAULT_SUCCESS_CODE)
                .message(DEFAULT_SUCCESS_MESSAGE)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> ok(String message) {
        return JsonResult.<T>Builder()
                .status(true)
                .code(DEFAULT_SUCCESS_CODE)
                .message(message)
                .data(null)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> ok(int code, String message) {
        return JsonResult.<T>Builder()
                .status(true)
                .code(code)
                .message(message)
                .data(null)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> ok(int code, String message, T data) {
        return JsonResult.<T>Builder()
                .status(true)
                .code(code)
                .message(message)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> JsonResult<T> error(String error) {
        return JsonResult.<T>Builder()
                .status(false)
                .code(DEFAULT_ERROR_CODE)
                .error(error)
                .data(null)
                .build();
    }

    public static <T> JsonResult<T> error(MessageEnum messageEnum) {
        return JsonResult.<T>Builder()
                .status(false)
                .code(messageEnum.getCode())
                .error(messageEnum.getMessage())
                .data(null)
                .build();
    }

    public static <T> JsonResult<T> error(int code, String error) {
        return JsonResult.<T>Builder()
                .status(false)
                .code(code)
                .data(null)
                .error(error)
                .build();
    }

    JsonResult (Builder<T> builder) {
        this.success = builder.success;
        this.code = builder.code;
        this.message = builder.message;
        this.error = builder.error;
        this.data = builder.data;
    }

    public static <T> Builder<T> Builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {

        private boolean success;
        private int code;
        private String message;
        private String error;
        private T data;

        Builder() {}

        Builder(JsonResult<T>  jsonResult) {
            this.success = jsonResult.success;
            this.code = jsonResult.code;
            this.message = jsonResult.message;
            this.error = jsonResult.error;
            this.data = jsonResult.data;
        }

        public Builder<T> status(boolean status) {
            this.success = status;
            return this;
        }

        public Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> error(String error) {
            this.error = error;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public JsonResult<T> build() {
            return new JsonResult<T>(this);
        }
    }

}
