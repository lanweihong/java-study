package com.lanweihong.springdoc.swagger.exception;

import com.lanweihong.springdoc.swagger.vo.JsonResult;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

/**
 * 全局异常处理
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 22:08
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonResult<String> bindInvalidParameterExceptionHandler(InvalidParameterException e) {
        log.error("错误的请求，参数无效：", e);
        return JsonResult.error(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public JsonResult<String> bindBadRequestExceptionHandler(BadRequestException e) {
        log.error("错误的请求：", e);
        return JsonResult.error(e.getError());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public JsonResult<String> bindNotFoundExceptionHandler(NotFoundException e) {
        log.error("资源未找到：", e);
        return JsonResult.error(e.getError());
    }

    // 其他异常处理......
}
