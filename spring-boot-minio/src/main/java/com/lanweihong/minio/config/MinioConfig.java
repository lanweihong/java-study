package com.lanweihong.minio.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lanweihong
 * @date 2022/1/4 16:16
 */
@Configuration
@EnableConfigurationProperties(MinioPropertiesConfig.class)
@Slf4j
public class MinioConfig {

    private MinioPropertiesConfig minioPropertiesConfig;

    @Autowired
    public void setMinioPropertiesConfig(MinioPropertiesConfig minioPropertiesConfig) {
        this.minioPropertiesConfig = minioPropertiesConfig;
    }

    @Bean
    public CustomMinioClient customMinioClient() {
        MinioClient minioClient;
        try {
            minioClient = MinioClient.builder()
                    .endpoint(minioPropertiesConfig.getEndpoint())
                    .credentials(minioPropertiesConfig.getAccessKey(), minioPropertiesConfig.getSecretKey())
                    .build();
        } catch (Exception e) {
            log.error("初始化 Minio 客户端失败：" + e.getMessage());
            throw e;
        }
        return new CustomMinioClient(minioClient);
    }

}
