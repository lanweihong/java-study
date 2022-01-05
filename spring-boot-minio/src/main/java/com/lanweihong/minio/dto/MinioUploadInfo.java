package com.lanweihong.minio.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 16:23
 */
@Getter
@Setter
@ToString
public class MinioUploadInfo {

    private String uploadId;
    private LocalDateTime expiryTime;
    private List<String> uploadUrls;
}
