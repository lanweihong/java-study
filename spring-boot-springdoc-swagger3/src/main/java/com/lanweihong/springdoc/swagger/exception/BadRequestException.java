package com.lanweihong.springdoc.swagger.exception;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 22:06
 */
public class BadRequestException extends BaseException {

    private int code;

    public BadRequestException(int code, String error) {
        super(code, error);
    }
}
