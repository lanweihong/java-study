package com.lanweihong.common.core.exception;

import com.lanweihong.common.base.enums.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lanweihong
 * @date 2021/12/25 15:32
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseErrorException extends RuntimeException {

    private static final long serialVersionUID = 6386720492655133851L;
    private int code;
    private String error;

    public BaseErrorException(MessageEnum messageEnum) {
        this.code = messageEnum.getCode();
        this.error = messageEnum.getMessage();
    }
}
