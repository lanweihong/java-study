package com.lanweihong.minio.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 16:21
 */
@Data
public class MinioOperationResult {

    private String url;

    /**
     * 状态：0-未上传，1-已上传，2-上传中
     */
    private Integer status;

    /**
     * 已上传分片列表
     */
    private List<Integer> chunkUploadedList;
}
