package com.lanweihong.minio.dao;

import com.lanweihong.common.core.mapper.BaseMapper;
import com.lanweihong.minio.entity.MinioFileChunkUploadInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 22:11
 */
@Repository
public interface IMinioFileChunkUploadInfoDao extends BaseMapper<MinioFileChunkUploadInfoDO> {

    /**
     * 批量添加
     * @param list
     * @return
     */
    int batchInsert(List<MinioFileChunkUploadInfoDO> list);

    /**
     * 根据文件 md5 查询列表
     * @param fileMd5 文件 md5
     * @return
     */
    List<MinioFileChunkUploadInfoDO> listByFileMd5(@Param("fileMd5")String fileMd5);

    /**
     * 根据文件 md5 和 uploadId 查询列表
     * @param fileMd5
     * @param uploadId
     * @return
     */
    List<MinioFileChunkUploadInfoDO> listByFileMd5AndUploadId(@Param("fileMd5")String fileMd5, @Param("uploadId")String uploadId);
}
