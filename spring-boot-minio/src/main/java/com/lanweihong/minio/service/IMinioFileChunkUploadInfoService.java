package com.lanweihong.minio.service;

import com.lanweihong.common.core.service.IBaseService;
import com.lanweihong.minio.dto.MinioFileChunkUploadInfoDTO;
import com.lanweihong.minio.entity.MinioFileChunkUploadInfoDO;
import com.lanweihong.minio.param.MinioFileChunkUploadInfoParam;

import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 22:47
 */
public interface IMinioFileChunkUploadInfoService extends IBaseService<MinioFileChunkUploadInfoDO> {

    /**
     * 保存
     * @param param
     * @return
     */
    boolean saveMinioFileChunkUploadInfo(MinioFileChunkUploadInfoParam param);

    List<MinioFileChunkUploadInfoDO> listByFileMd5(String fileMd5);

    List<MinioFileChunkUploadInfoDTO> listByFileMd5AndUploadId(String fileMd5, String uploadId);
}
