package com.lanweihong.minio.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.minio.dto.MinioFileChunkUploadInfoDTO;
import com.lanweihong.minio.dto.MinioFileUploadInfoDTO;
import com.lanweihong.minio.dto.MinioOperationResult;
import com.lanweihong.minio.dto.MinioUploadInfo;
import com.lanweihong.minio.entity.MinioFileUploadInfoDO;
import com.lanweihong.minio.enums.CommonEnums;
import com.lanweihong.minio.helper.MinioHelper;
import com.lanweihong.minio.param.GetMinioUploadInfoParam;
import com.lanweihong.minio.param.MergeMinioMultipartParam;
import com.lanweihong.minio.param.MinioFileChunkUploadInfoParam;
import com.lanweihong.minio.param.MinioFileUploadInfoParam;
import com.lanweihong.minio.service.IFileService;
import com.lanweihong.minio.service.IMinioFileChunkUploadInfoService;
import com.lanweihong.minio.service.IMinioFileUploadInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lanweihong
 * @date 2022/1/4 16:24
 */
@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    private final MinioHelper minioHelper;
    private final IMinioFileUploadInfoService minioFileUploadInfoService;
    private final IMinioFileChunkUploadInfoService minioFileChunkUploadInfoService;

    @Autowired
    public FileServiceImpl(MinioHelper minioHelper, IMinioFileUploadInfoService minioFileUploadInfoService, IMinioFileChunkUploadInfoService minioFileChunkUploadInfoService) {
        this.minioHelper = minioHelper;
        this.minioFileUploadInfoService = minioFileUploadInfoService;
        this.minioFileChunkUploadInfoService = minioFileChunkUploadInfoService;
    }

    @Override
    public MinioUploadInfo getUploadId(GetMinioUploadInfoParam param) {
        MinioUploadInfo uploadInfo;
        MinioFileUploadInfoDTO minioFileUploadInfo = this.minioFileUploadInfoService.getByFileMd5(param.getFileMd5());
        if (null == minioFileUploadInfo) {
            // 计算分片数量
            double partCount = Math.ceil(param.getFileSize() / param.getChunkSize());
            log.info("总分片数：" + partCount);
            uploadInfo = minioHelper.initMultiPartUpload(param.getFileName(), (int) partCount, param.getContentType());
            if (null != uploadInfo) {
                MinioFileUploadInfoParam saveParam = new MinioFileUploadInfoParam();
                saveParam.setUploadId(uploadInfo.getUploadId());
                saveParam.setFileMd5(param.getFileMd5());
                saveParam.setFileName(param.getFileName());
                saveParam.setTotalChunk((int) partCount);
                saveParam.setFileStatus(CommonEnums.MinioFileStatusEnum.UN_UPLOADED.ordinal());
                // 保存文件上传信息
                minioFileUploadInfoService.saveMinioFileUploadInfo(saveParam);

                MinioFileChunkUploadInfoParam chunkUploadInfoParam = new MinioFileChunkUploadInfoParam();
                chunkUploadInfoParam.setUploadUrls(uploadInfo.getUploadUrls());
                chunkUploadInfoParam.setUploadId(uploadInfo.getUploadId());
                chunkUploadInfoParam.setExpiryTime(uploadInfo.getExpiryTime());
                chunkUploadInfoParam.setFileMd5(param.getFileMd5());
                chunkUploadInfoParam.setFileName(param.getFileName());
                // 保存分片上传信息
                minioFileChunkUploadInfoService.saveMinioFileChunkUploadInfo(chunkUploadInfoParam);
            }
            return uploadInfo;
        }
        // 查询分片上传地址
        List<MinioFileChunkUploadInfoDTO> list = minioFileChunkUploadInfoService.listByFileMd5AndUploadId(minioFileUploadInfo.getFileMd5(), minioFileUploadInfo.getUploadId());
        List<String> uploadUrlList = list.stream().map(MinioFileChunkUploadInfoDTO::getChunkUploadUrl).collect(Collectors.toList());
        uploadInfo = new MinioUploadInfo();
        uploadInfo.setUploadUrls(uploadUrlList);
        uploadInfo.setUploadId(minioFileUploadInfo.getUploadId());
        return uploadInfo;
    }

    @Override
    public MinioOperationResult checkFileExistsByMd5(String md5) {
        MinioOperationResult result = new MinioOperationResult();
        MinioFileUploadInfoDTO minioFileUploadInfo = this.minioFileUploadInfoService.getByFileMd5(md5);
        if (null == minioFileUploadInfo) {
            result.setStatus(CommonEnums.MinioFileStatusEnum.UN_UPLOADED.ordinal());
            return result;
        }
        // 已上传
        if (minioFileUploadInfo.getFileStatus() == CommonEnums.MinioFileStatusEnum.UPLOADED.ordinal()) {
            result.setStatus(CommonEnums.MinioFileStatusEnum.UPLOADED.ordinal());
            result.setUrl(minioFileUploadInfo.getFileUrl());
            return result;
        }
        // 查询已上传分片列表并返回已上传列表
        List<Integer> chunkUploadedList = listUploadParts(minioFileUploadInfo.getFileName(), minioFileUploadInfo.getUploadId());
        result.setStatus(CommonEnums.MinioFileStatusEnum.UPLOADING.ordinal());
        result.setChunkUploadedList(chunkUploadedList);
        return result;
    }

    @Override
    public List<Integer> listUploadParts(String objectName, String uploadId) {
        return minioHelper.listUploadChunkList(objectName, uploadId);
    }

    @Override
    public String mergeMultipartUpload(MergeMinioMultipartParam param) {
        String result = minioHelper.mergeMultiPartUpload(param.getFileName(), param.getUploadId());
        if (!StringUtils.isEmpty(result)) {
            MinioFileUploadInfoParam fileUploadInfoParam = new MinioFileUploadInfoParam();
            fileUploadInfoParam.setFileUrl(result);
            fileUploadInfoParam.setFileMd5(param.getMd5());
            fileUploadInfoParam.setFileStatus(CommonEnums.MinioFileStatusEnum.UPLOADED.ordinal());

            // 更新状态
            minioFileUploadInfoService.updateFileStatusByFileMd5(fileUploadInfoParam);
        }
        return result;
    }
}
