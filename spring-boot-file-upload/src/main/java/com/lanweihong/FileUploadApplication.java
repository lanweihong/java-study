package com.lanweihong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lanweihong
 * @date 2021/12/25 14:57
 */
@SpringBootApplication
@MapperScan("com.lanweihong.dao")
public class FileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadApplication.class, args);
    }
}
