package com.lanweihong.aop.entity;

import com.lanweihong.common.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 审计日志实体类
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:22
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Table(name = "audit_log")
public class AuditLogDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8364702890093931325L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "description")
    private String description;

    @Column(name = "operation_type")
    private Integer operationType;

    @Column(name = "module")
    private Integer module;

    @Transient
    private String moduleName;

    @Column(name = "ip")
    private String ip;

    @Column(name = "user_agent")
    private String userAgent;
}
