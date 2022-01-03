package com.lanweihong.service;

import com.lanweihong.param.FileChunkParam;

/**
 * @author lanweihong
 * @date 2021/12/23 3:37
 */
public interface IFileService {

    /**
     * 上传文件
     * @param param 参数
     * @return
     */
    boolean uploadFile(FileChunkParam param);
}
