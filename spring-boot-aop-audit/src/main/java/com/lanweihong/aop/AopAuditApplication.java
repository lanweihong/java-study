package com.lanweihong.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 12:53
 */
@SpringBootApplication
@MapperScan("com.lanweihong.aop.dao")
public class AopAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopAuditApplication.class, args);
    }
}
