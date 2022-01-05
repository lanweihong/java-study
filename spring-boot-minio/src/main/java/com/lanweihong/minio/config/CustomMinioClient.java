package com.lanweihong.minio.config;

import com.google.common.collect.Multimap;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import io.minio.messages.Part;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author lanweihong
 * @date 2022/1/4 16:16
 */
public class CustomMinioClient extends MinioClient {

    protected CustomMinioClient(MinioClient client) {
        super(client);
    }

    /**
     * 获取 uploadId
     * @param bucketName bucketName
     * @param region region
     * @param objectName objectName
     * @param headers headers
     * @param extraQueryParams extraQueryParams
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws XmlParserException
     * @throws InvalidResponseException
     * @throws InternalException
     */
    public String getUploadId(String bucketName, String region, String objectName,
                              Multimap<String, String> headers, Multimap<String, String> extraQueryParams)
            throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        CreateMultipartUploadResponse response = this.createMultipartUpload(bucketName, region, objectName, headers, extraQueryParams);

        return response.result().uploadId();
    }

    /**
     * 合并分片
     * @param bucketName
     * @param region
     * @param objectName
     * @param uploadId
     * @param parts
     * @param extraHeaders
     * @param extraQueryParams
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws XmlParserException
     * @throws InvalidResponseException
     * @throws InternalException
     */
    public ObjectWriteResponse mergeMultipart(String bucketName, String region, String objectName, String uploadId,
                                              Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams)
            throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return this.completeMultipartUpload(bucketName, region, objectName, uploadId, parts,extraHeaders, extraQueryParams);
    }

    /**
     * 查询分分片列表
     * @param bucketName
     * @param region
     * @param objectName
     * @param maxParts
     * @param partNumberMaker
     * @param uploadId
     * @param extraHeaders
     * @param extraQueryParams
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws XmlParserException
     * @throws InvalidResponseException
     * @throws InternalException
     */
    public ListPartsResponse listMultipart(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMaker,
                                           String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, XmlParserException, InvalidResponseException, InternalException {
        return this.listParts(bucketName, region, objectName, maxParts, partNumberMaker, uploadId, extraHeaders, extraQueryParams);
    }
}
