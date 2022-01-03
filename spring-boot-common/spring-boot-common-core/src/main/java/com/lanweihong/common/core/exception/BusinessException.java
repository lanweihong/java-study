package com.lanweihong.common.core.exception;

import com.lanweihong.common.base.enums.MessageEnum;
import lombok.Data;

/**
 * @author lanweihong
 * @date 2021/12/25 15:32
 */
@Data
public class BusinessException extends BaseErrorException {

    private static final long serialVersionUID = 2369773524406947262L;

    public BusinessException(MessageEnum messageEnum) {
        super(messageEnum);
    }

    public BusinessException(String error) {
        super.setCode(-1);
        super.setError(error);
    }
}
