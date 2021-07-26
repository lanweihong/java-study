package com.lanweihong.springdoc.swagger.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 21:59
 */
@Getter
@Setter
public class BaseException extends Exception {

    private int code;
    private String error;

    public BaseException(int code, String error) {
        super(error);
        this.code = code;
        this.error = error;
    }
}
