package com.lanweihong.minio.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lanweihong
 * @date 2022/1/4 16:19
 */
@Data
public class GetMinioUploadInfoParam {

    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @NotBlank(message = "文件MD5不能为空")
    private String fileMd5;

    @NotNull(message = "文件大小不能为空")
    private Double fileSize;

    @NotNull(message = "分片大小不能为空")
    private Double chunkSize;

    @NotBlank(message = "Content-Type不能为空")
    private String contentType;
}
