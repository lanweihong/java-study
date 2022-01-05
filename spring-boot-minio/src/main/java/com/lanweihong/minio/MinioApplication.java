package com.lanweihong.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lanweihong
 * @date 2022/1/4 16:12
 */
@SpringBootApplication
@MapperScan("com.lanweihong.minio.dao")
public class MinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinioApplication.class, args);
    }
}
