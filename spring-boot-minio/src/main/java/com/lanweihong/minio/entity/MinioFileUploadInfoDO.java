package com.lanweihong.minio.entity;

import com.lanweihong.common.core.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author lanweihong
 * @date 2022/1/4 21:52
 */
@Data
@Table(name = "minio_file_upload_info")
public class MinioFileUploadInfoDO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3756796969530960728L;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_md5")
    private String fileMd5;

    @Column(name = "file_status")
    private Integer fileStatus;

    @Column(name = "upload_id")
    private String uploadId;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "total_chunk")
    private Integer totalChunk;
}
