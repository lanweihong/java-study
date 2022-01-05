package com.lanweihong.minio.service;

import com.lanweihong.common.core.service.IBaseService;
import com.lanweihong.minio.dto.MinioFileUploadInfoDTO;
import com.lanweihong.minio.entity.MinioFileUploadInfoDO;
import com.lanweihong.minio.param.MinioFileUploadInfoParam;

/**
 * @author lanweihong
 * @date 2022/1/4 22:17
 */
public interface IMinioFileUploadInfoService extends IBaseService<MinioFileUploadInfoDO> {

    /**
     * 保存
     * @param param 参数对象
     * @return
     */
    MinioFileUploadInfoDTO saveMinioFileUploadInfo(MinioFileUploadInfoParam param);

    /**
     * 修改文件状态
     * @param param 参数对象
     * @return
     */
    int updateFileStatusByFileMd5(MinioFileUploadInfoParam param);

    /**
     * 根据文件 md5 查询
     * @param fileMd5 文件 md5
     * @return
     */
    MinioFileUploadInfoDTO getByFileMd5(String fileMd5);
}
