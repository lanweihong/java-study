package com.lanweihong.minio.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lanweihong
 * @date 2022/1/4 16:22
 */
@Data
public class MergeMinioMultipartParam {

    @NotBlank(message = "文件名不能为空")
    private String fileName;

    @NotBlank(message = "uploadId 不能为空")
    private String uploadId;

    @NotBlank(message = "文件md5不能为空")
    private String md5;
}
