package com.lanweihong.minio.exception;

import com.lanweihong.common.base.enums.MessageEnum;
import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.common.core.exception.BaseErrorException;
import com.lanweihong.common.core.exception.BusinessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author lanweihong
 * @date 2022/1/5 5:25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数校验错误异常处理
     * @param exception 异常
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public JsonResult<String> bindExceptionHandler(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            if (null != bindingResult.getFieldError()) {
                return JsonResult.error(bindingResult.getFieldError().getDefaultMessage());
            }
        }
        return JsonResult.error(MessageEnum.PARAM_INVALID);
    }

    /**
     *  ServiceErrorException - 服务错误
     * @param exception 异常
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public JsonResult<String> errorExceptionHandler(BaseErrorException exception) {
        return JsonResult.error(exception.getCode(), exception.getError());
    }

    /**
     * 请求中缺失参数异常，如 @RequestParam("userName")
     * @param exception MissingServletRequestParameterException
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public JsonResult<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        return JsonResult.error(MessageEnum.PARAM_NOT_NULL);
    }

}
