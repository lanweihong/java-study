package com.lanweihong.common.base.enums;

import lombok.Getter;

/**
 * 消息枚举
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:09
 */
public enum MessageEnum {

    /**
     * 消息枚举
     */
    FAIL(-1, "操作失败"),
    SUCCESS(200, "操作成功"),
    RECORD_NOT_EXISTED(1001, "记录不存在"),
    PARAM_NOT_NULL(1002, "参数不能为空"),
    PARAM_INVALID(1003, "参数错误"),
    UPLOAD_FILE_NOT_NULL(1004, "上传文件不能为空");

    MessageEnum(int value, String text) {
        this.code = value;
        this.message = text;
    }

    @Getter
    private final int code;

    @Getter
    private final String message;

    public static MessageEnum valueOf(int value) {
        MessageEnum[] enums = values();
        for (MessageEnum enumItem : enums) {
            if (value == enumItem.getCode()) {
                return enumItem;
            }
        }
        return null;
    }
}
