package com.lanweihong.minio.service.impl;

import com.lanweihong.common.core.service.impl.BaseServiceImpl;
import com.lanweihong.common.core.utils.HongBeanUtils;
import com.lanweihong.minio.dao.IMinioFileChunkUploadInfoDao;
import com.lanweihong.minio.dto.MinioFileChunkUploadInfoDTO;
import com.lanweihong.minio.entity.MinioFileChunkUploadInfoDO;
import com.lanweihong.minio.param.MinioFileChunkUploadInfoParam;
import com.lanweihong.minio.service.IMinioFileChunkUploadInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanweihong
 * @date 2022/1/4 22:53
 */
@Service
public class MinioFileChunkUploadInfoServiceImpl extends BaseServiceImpl<MinioFileChunkUploadInfoDO> implements IMinioFileChunkUploadInfoService {

    private final IMinioFileChunkUploadInfoDao minioFileChunkUploadInfoDao;

    @Autowired
    public MinioFileChunkUploadInfoServiceImpl(IMinioFileChunkUploadInfoDao minioFileChunkUploadInfoDao) {
        this.minioFileChunkUploadInfoDao = minioFileChunkUploadInfoDao;
    }

    @Override
    public boolean saveMinioFileChunkUploadInfo(MinioFileChunkUploadInfoParam param) {
        List<MinioFileChunkUploadInfoDO> list = new ArrayList<>();
        for (int i = 0; i < param.getUploadUrls().size(); i++) {
            MinioFileChunkUploadInfoDO tempObj = new MinioFileChunkUploadInfoDO();
            tempObj.setChunkNumber(i + 1);
            tempObj.setFileMd5(param.getFileMd5());
            tempObj.setUploadId(param.getUploadId());
            tempObj.setExpiryTime(param.getExpiryTime());
            tempObj.setChunkUploadUrl(param.getUploadUrls().get(i));
            list.add(tempObj);
        }
        int result = this.minioFileChunkUploadInfoDao.batchInsert(list);
        return result != 0;
    }

    @Override
    public List<MinioFileChunkUploadInfoDO> listByFileMd5(String fileMd5) {
        return minioFileChunkUploadInfoDao.listByFileMd5(fileMd5);
    }

    @Override
    public List<MinioFileChunkUploadInfoDTO> listByFileMd5AndUploadId(String fileMd5, String uploadId) {
        List<MinioFileChunkUploadInfoDO> list = minioFileChunkUploadInfoDao.listByFileMd5AndUploadId(fileMd5, uploadId);
        return HongBeanUtils.doListToDtoList(list, MinioFileChunkUploadInfoDTO.class);
    }
}
