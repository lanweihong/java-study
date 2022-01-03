DROP TABLE IF EXISTS `file_chunk`;
CREATE TABLE `file_chunk`  (
                               `id` bigint unsigned NOT NULL,
                               `version` tinyint(1) DEFAULT NULL,
                               `status` tinyint(1) DEFAULT NULL,
                               `update_time` datetime(0) DEFAULT NULL,
                               `add_time` datetime(0) DEFAULT NULL,
                               `chunk_number` int(0) DEFAULT NULL COMMENT '当前分片，从1开始',
                               `chunk_size` float DEFAULT NULL COMMENT '分片大小',
                               `current_chunk_size` float DEFAULT NULL COMMENT '当前分片大小',
                               `total_chunk` int(0) DEFAULT NULL COMMENT '总分片数',
                               `identifier` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件标识',
                               `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
                               `file_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件类型',
                               `relative_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;