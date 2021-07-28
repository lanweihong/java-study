package com.lanweihong.aop.enums;

import lombok.Getter;

/**
 * 审计日志操作类型枚举
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:27
 */
public enum AuditLogOperationTypeEnum {

    /**
     * 定义审计日志操作类型枚举
     */
    LOGIN(1, "登录"),
    LOGOUT(2, "注销登录"),
    INSERT(3, "新增"),
    UPDATE(4, "修改"),
    DELETE(5, "删除"),
    QUERY(6, "查询");

    AuditLogOperationTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    @Getter
    private final int typeCode;

    @Getter
    private final String typeName;
}
