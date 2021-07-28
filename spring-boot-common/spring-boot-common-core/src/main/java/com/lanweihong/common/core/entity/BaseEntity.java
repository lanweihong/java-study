package com.lanweihong.common.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lanweihong.common.core.enums.DbRecordStatusEnum;
import com.lanweihong.common.core.utils.UniqueIdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:11
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -2067539248964297683L;

    public BaseEntity() {
        this.id = UniqueIdGenerator.INSTANCE.nextId();
        this.status = DbRecordStatusEnum.NORMAL.ordinal();
        this.addTime = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version")
    @JsonIgnore
    private Integer version;

    @Column(name = "status")
    private Integer status;

    @Column(name = "add_time")
    private LocalDateTime addTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Transient
    @JsonIgnore
    private boolean isNew() {
        return this.id == null;
    }
}
