package com.lanweihong.entity;

import com.lanweihong.common.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 文件分片实体类
* @author lanweihong
*/
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "file_chunk")
public class FileChunkDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3907706848251401314L;
    @Column(name = "chunk_number")
    private Integer chunkNumber;

    @Column(name = "chunk_size")
    private Float chunkSize;

    @Column(name = "current_chunk_size")
    private Float currentChunkSize;

    @Column(name = "total_chunk")
    private Integer totalChunks;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "relative_path")
    private String relativePath;
}
