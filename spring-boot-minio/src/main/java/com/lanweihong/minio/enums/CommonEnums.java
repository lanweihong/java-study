package com.lanweihong.minio.enums;

/**
 * @author lanweihong
 * @date 2022/1/5 5:46
 */
public class CommonEnums {

    public enum MinioFileStatusEnum {
        /**
         * 待上传
         * 已上传
         * 上传中
         */
        UN_UPLOADED,
        UPLOADED,
        UPLOADING
    }
}
