package com.lanweihong.minio.helper;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.common.collect.HashMultimap;
import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.common.core.exception.BusinessException;
import com.lanweihong.minio.config.CustomMinioClient;
import com.lanweihong.minio.config.MinioPropertiesConfig;
import com.lanweihong.minio.dto.MinioOperationResult;
import com.lanweihong.minio.dto.MinioUploadInfo;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lanweihong
 * @date 2022/1/4 16:26
 */
@Component
@Slf4j
public class MinioHelper {

    private final MinioPropertiesConfig minioPropertiesConfig;
    private final CustomMinioClient customMinioClient;

    @Autowired
    public MinioHelper(MinioPropertiesConfig minioPropertiesConfig, CustomMinioClient customMinioClient) {
        this.minioPropertiesConfig = minioPropertiesConfig;
        this.customMinioClient = customMinioClient;
    }

    /**
     * 初始化获取 uploadId
     * @param objectName 文件名
     * @param partCount 分片总数
     * @param contentType contentType
     * @return
     */
    public MinioUploadInfo initMultiPartUpload(String objectName, int partCount, String contentType) {
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);

        String uploadId = "";
        List<String> partUrlList = new ArrayList<>();
        try {
            // 获取 uploadId
            uploadId = customMinioClient.getUploadId(minioPropertiesConfig.getBucketName(),
                    null,
                    objectName,
                    headers,
                    null);
            Map<String, String> paramsMap = new HashMap<>(2);
            paramsMap.put("uploadId", uploadId);
            for (int i = 1; i <= partCount; i++) {
                paramsMap.put("partNumber", String.valueOf(i));
                // 获取上传 url
                String uploadUrl = customMinioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        // 注意此处指定请求方法为 PUT，前端需对应，否则会报 `SignatureDoesNotMatch` 错误
                        .method(Method.PUT)
                        .bucket(minioPropertiesConfig.getBucketName())
                        .object(objectName)
                        // 指定上传连接有效期
                        .expiry(minioPropertiesConfig.getChunkUploadExpirySecond(), TimeUnit.SECONDS)
                        .extraQueryParams(paramsMap).build());

                partUrlList.add(uploadUrl);
            }
        } catch (Exception e) {
            log.error("initMultiPartUpload Error:" + e);
           return null;
        }
        // 过期时间
        LocalDateTime expireTime = LocalDateTimeUtil.offset(LocalDateTime.now(), minioPropertiesConfig.getChunkUploadExpirySecond(), ChronoUnit.SECONDS);
        MinioUploadInfo result = new MinioUploadInfo();
        result.setUploadId(uploadId);
        result.setExpiryTime(expireTime);
        result.setUploadUrls(partUrlList);
        return result;
    }

    /**
     * 分片合并
     * @param objectName 文件名
     * @param uploadId uploadId
     * @return
     */
    public String mergeMultiPartUpload(String objectName, String uploadId) {
        // todo 最大1000分片 这里好像可以改吧
        Part[] parts = new Part[1000];
        int partIndex = 0;
        ListPartsResponse partsResponse = listUploadPartsBase(objectName, uploadId);
        if (null == partsResponse) {
            log.error("查询文件分片列表为空");
            throw new BusinessException("分片列表为空");
        }
        for (Part partItem : partsResponse.result().partList()) {
            parts[partIndex] = new Part(partIndex + 1, partItem.etag());
            partIndex++;
        }
        ObjectWriteResponse objectWriteResponse;
        try {
            objectWriteResponse = customMinioClient.mergeMultipart(minioPropertiesConfig.getBucketName(), null, objectName, uploadId, parts, null, null);
        } catch (Exception e) {
            log.error("分片合并失败：" + e);
            throw new BusinessException("分片合并失败：" + e.getMessage());
        }
        if (null == objectWriteResponse) {
            log.error("合并失败，合并结果为空");
            throw new BusinessException("分片合并失败");
        }
        return objectWriteResponse.region();
    }

    /**
     * 获取已上传的分片列表
     * @param objectName 文件名
     * @param uploadId uploadId
     * @return
     */
    public List<Integer> listUploadChunkList(String objectName, String uploadId) {
        ListPartsResponse partsResponse = listUploadPartsBase(objectName, uploadId);
        if (null == partsResponse) {
            return Collections.emptyList();
        }
        return partsResponse.result().partList().stream().map(Part::partNumber).collect(Collectors.toList());
    }

    private ListPartsResponse listUploadPartsBase(String objectName, String uploadId) {
        int maxParts = 1000;
        ListPartsResponse partsResponse;
        try {
            partsResponse = customMinioClient.listMultipart(minioPropertiesConfig.getBucketName(), null, objectName, maxParts, 0, uploadId, null, null);
        } catch (ServerException | InsufficientDataException | ErrorResponseException | NoSuchAlgorithmException | IOException | XmlParserException | InvalidKeyException | InternalException | InvalidResponseException e) {
            log.error("查询文件分片列表错误：{}，uploadId:{}", e, uploadId);
            return null;
        }
        return partsResponse;
    }
}
