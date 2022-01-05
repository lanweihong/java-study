package com.lanweihong.minio.service;

import com.lanweihong.minio.dto.MinioOperationResult;
import com.lanweihong.minio.dto.MinioUploadInfo;
import com.lanweihong.minio.param.GetMinioUploadInfoParam;
import com.lanweihong.minio.param.MergeMinioMultipartParam;

import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 16:18
 */
public interface IFileService {

    /**
     * 获取分片上传信息
     * @param param 参数
     * @return
     */
    MinioUploadInfo getUploadId(GetMinioUploadInfoParam param);

    /**
     * 检查文件是否存在
     * @param md5 md5
     * @return
     */
    MinioOperationResult checkFileExistsByMd5(String md5);

    /**
     * 查询已上传的分片序号
     * @param objectName 文件名
     * @param uploadId uploadId
     * @return
     */
    List<Integer> listUploadParts(String objectName, String uploadId);

    /**
     * 分片合并
     * @param param 参数
     * @return
     */
    String mergeMultipartUpload(MergeMinioMultipartParam param);
}
