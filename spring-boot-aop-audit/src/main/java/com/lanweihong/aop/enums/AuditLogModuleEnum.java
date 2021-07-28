package com.lanweihong.aop.enums;

import lombok.Getter;

/**
 * 审计日志模块
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 18:01
 */
public enum AuditLogModuleEnum {

    /**
     * 定义审计日志模块
     */
    LOGIN(0, "登录"),
    USER(1, "用户模块"),
    ROLE(2, "角色模块"),
    PERMISSION(3, "权限模块");

    AuditLogModuleEnum(int moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    @Getter
    private final int moduleCode;

    @Getter
    private final String moduleName;
}
