package com.lanweihong.encrypt.entity;

import com.lanweihong.common.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/10/13 16:21
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Table(name = "book")
public class BookDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3117129058212884219L;

    @Column(name = "book_name")
    private String bookName;
}
