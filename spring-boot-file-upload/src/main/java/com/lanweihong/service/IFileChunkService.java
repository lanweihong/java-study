package com.lanweihong.service;

import com.lanweihong.dto.FileChunkDTO;
import com.lanweihong.entity.FileChunkDO;
import com.lanweihong.param.FileChunkParam;

import java.util.List;

/**
* @author lanweihong
*/
public interface IFileChunkService {

    /**
     * 根据文件 md5 查询
     *
     * @param md5 md5
     * @return
     */
    List<FileChunkDTO> listByFileMd5(String md5);

    /**
     * 保存记录
     *
     * @param param 记录参数
     */
    void saveFileChunk(FileChunkParam param);

    /**
     * 删除记录
     *
     * @param fileChunk fileChunk
     * @return
     */
    int delete(FileChunkDO fileChunk);

    /**
     * 根据 id 删除
     *
     * @param id id
     * @return
     */
    int deleteById(Long id);

}
