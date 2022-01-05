DROP TABLE IF EXISTS `minio_file_upload_info`;
CREATE TABLE `minio_file_upload_info`  (
                                           `id` bigint unsigned NOT NULL,
                                           `version` tinyint(1) DEFAULT NULL,
                                           `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
                                           `file_md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件MD5',
                                           `file_status` tinyint(0) DEFAULT NULL,
                                           `upload_id` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'uploadId',
                                           `file_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件访问URL',
                                           `total_chunk` int(0) DEFAULT NULL COMMENT '总分片数',
                                           `status` tinyint(1) DEFAULT NULL,
                                           `update_time` datetime(0) DEFAULT NULL,
                                           `add_time` datetime(0) DEFAULT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `minio_file_chunk_upload_info`;
CREATE TABLE `minio_file_chunk_upload_info`  (
                                                 `id` bigint unsigned NOT NULL,
                                                 `version` tinyint(1) DEFAULT NULL,
                                                 `file_md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
                                                 `chunk_number` int(0) DEFAULT NULL COMMENT '分片序号',
                                                 `chunk_upload_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分片上传地址',
                                                 `expiry_time` datetime(0) DEFAULT NULL COMMENT '过期时间',
                                                 `upload_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'uploadId',
                                                 `status` tinyint(1) DEFAULT NULL,
                                                 `update_time` datetime(0) DEFAULT NULL,
                                                 `add_time` datetime(0) DEFAULT NULL,
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

