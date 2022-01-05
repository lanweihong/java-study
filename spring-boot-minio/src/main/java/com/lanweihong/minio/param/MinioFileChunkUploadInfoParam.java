package com.lanweihong.minio.param;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/5 1:44
 */
@Data
public class MinioFileChunkUploadInfoParam {

    private String fileName;
    private String fileMd5;
    private LocalDateTime expiryTime;
    private List<String> uploadUrls;
    private String uploadId;
}
