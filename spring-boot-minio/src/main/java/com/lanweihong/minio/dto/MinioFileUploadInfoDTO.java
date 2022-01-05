package com.lanweihong.minio.dto;

import lombok.Data;

/**
 * @author lanweihong
 * @date 2022/1/5 5:00
 */
@Data
public class MinioFileUploadInfoDTO {

    private Long id;
    private String fileName;
    private String fileMd5;
    private Integer fileStatus;
    private String uploadId;
    private Integer totalChunk;
    private String fileUrl;
}
