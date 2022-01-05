package com.lanweihong.minio.entity;

import com.lanweihong.common.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lanweihong
 * @date 2022/1/4 22:00
 */
@Data
@Table(name = "minio_file_chunk_upload_info")
public class MinioFileChunkUploadInfoDO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4039659488574104130L;

    @Column(name = "file_md5")
    private String fileMd5;

    @Column(name = "chunk_number")
    private Integer chunkNumber;

    @Column(name = "chunk_upload_url")
    private String chunkUploadUrl;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @Column(name = "upload_id")
    private String uploadId;
}
