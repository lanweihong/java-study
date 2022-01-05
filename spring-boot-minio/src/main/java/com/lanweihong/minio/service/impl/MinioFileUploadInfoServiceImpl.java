package com.lanweihong.minio.service.impl;

import com.lanweihong.common.base.enums.MessageEnum;
import com.lanweihong.common.core.exception.BusinessException;
import com.lanweihong.common.core.service.impl.BaseServiceImpl;
import com.lanweihong.common.core.utils.HongBeanUtils;
import com.lanweihong.minio.dao.IMinioFileUploadInfoDao;
import com.lanweihong.minio.dto.MinioFileUploadInfoDTO;
import com.lanweihong.minio.entity.MinioFileUploadInfoDO;
import com.lanweihong.minio.param.MinioFileUploadInfoParam;
import com.lanweihong.minio.service.IMinioFileUploadInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author lanweihong
 * @date 2022/1/4 22:22
 */
@Service
public class MinioFileUploadInfoServiceImpl extends BaseServiceImpl<MinioFileUploadInfoDO> implements IMinioFileUploadInfoService {

    private final IMinioFileUploadInfoDao minioFileUploadInfoDao;

    @Autowired
    public MinioFileUploadInfoServiceImpl(IMinioFileUploadInfoDao minioFileUploadInfoDao) {
        this.minioFileUploadInfoDao = minioFileUploadInfoDao;
    }

    @Override
    public MinioFileUploadInfoDTO saveMinioFileUploadInfo(MinioFileUploadInfoParam param) {
        MinioFileUploadInfoDO minioFileUploadInfo = null;
        if (null == param.getId()) {
            minioFileUploadInfo = new MinioFileUploadInfoDO();
        } else {
            minioFileUploadInfo = this.minioFileUploadInfoDao.selectByPrimaryKey(param.getId());
            if (null == minioFileUploadInfo) {
                throw new BusinessException(MessageEnum.RECORD_NOT_EXISTED);
            }
            minioFileUploadInfo.setUpdateTime(LocalDateTime.now());
        }
        BeanUtils.copyProperties(param, minioFileUploadInfo, "id");
        int result;
        if (null == param.getId()) {
            result = this.minioFileUploadInfoDao.insert(minioFileUploadInfo);
        } else {
            result = this.minioFileUploadInfoDao.updateByPrimaryKeySelective(minioFileUploadInfo);
        }
        if (result == 0) {
            throw new BusinessException(MessageEnum.FAIL);
        }
        return HongBeanUtils.doToDto(minioFileUploadInfo, MinioFileUploadInfoDTO.class);
    }

    @Override
    public int updateFileStatusByFileMd5(MinioFileUploadInfoParam param) {
        MinioFileUploadInfoDO minioFileUploadInfo = this.minioFileUploadInfoDao.getByFileMd5(param.getFileMd5());
        if (null == minioFileUploadInfo) {
            throw new BusinessException(MessageEnum.RECORD_NOT_EXISTED);
        }
        minioFileUploadInfo.setFileStatus(param.getFileStatus());
        minioFileUploadInfo.setFileUrl(param.getFileUrl());
        return this.minioFileUploadInfoDao.updateByPrimaryKeySelective(minioFileUploadInfo);
    }

    @Override
    public MinioFileUploadInfoDTO getByFileMd5(String fileMd5) {
        MinioFileUploadInfoDO minioFileUploadInfo = this.minioFileUploadInfoDao.getByFileMd5(fileMd5);
        if (null == minioFileUploadInfo) {
            return null;
        }
        return HongBeanUtils.doToDto(minioFileUploadInfo, MinioFileUploadInfoDTO.class);
    }
}
