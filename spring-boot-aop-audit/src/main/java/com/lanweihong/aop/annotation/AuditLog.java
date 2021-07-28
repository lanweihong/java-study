package com.lanweihong.aop.annotation;

import com.lanweihong.aop.enums.AuditLogModuleEnum;
import com.lanweihong.aop.enums.AuditLogOperationTypeEnum;

import java.lang.annotation.*;

/**
 * 审计日志注解
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 12:55
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {

    /**
     * 日志描述
     * @return
     */
    String description() default "";

    /**
     * 审计日志模块（登录/用户模块/角色模块等）
     * @return
     */
    AuditLogModuleEnum module() default AuditLogModuleEnum.USER;

    /**
     * 审计日志类型（登录/新增/修改/删除等）
     * @return
     */
    AuditLogOperationTypeEnum type() default AuditLogOperationTypeEnum.QUERY;
}
