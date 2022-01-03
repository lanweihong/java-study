package com.lanweihong.dao;

import com.lanweihong.common.core.mapper.BaseMapper;
import com.lanweihong.entity.FileChunkDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lanweihong
 */
@Repository
public interface IFileChunkDao extends BaseMapper<FileChunkDO> {

    /**
     * 通过 md5 查询记录
     *
     * @param md5 md5
     * @return
     */
    List<FileChunkDO> listByMd5(@Param("md5") String md5);

    /**
     * 批量新增记录
     *
     * @param fileChunkList fileChunkList
     * @return
     */
    int batchInsert(@Param("list") List<FileChunkDO> fileChunkList);

    /**
     * 删除记录
     *
     * @param fileChunk fileChunk
     * @return
     */
    @Override
    int delete(FileChunkDO fileChunk);
}
