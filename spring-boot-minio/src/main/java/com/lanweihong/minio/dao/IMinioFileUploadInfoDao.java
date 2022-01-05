package com.lanweihong.minio.dao;

import com.lanweihong.common.core.mapper.BaseMapper;
import com.lanweihong.minio.entity.MinioFileUploadInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lanweihong
 * @date 2022/1/4 22:02
 */
@Repository
public interface IMinioFileUploadInfoDao extends BaseMapper<MinioFileUploadInfoDO> {

    /**
     * 根据文件 md5 查询
     * @param fileMd5 文件 md5
     * @return
     */
    MinioFileUploadInfoDO getByFileMd5(@Param("md5")String fileMd5);
}
