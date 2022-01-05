package com.lanweihong.minio.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lanweihong
 * @date 2022/1/5 5:02
 */
@Data
public class MinioFileChunkUploadInfoDTO {

    private Integer chunkNumber;
    private String chunkUploadUrl;
    private LocalDateTime expiryTime;

}
