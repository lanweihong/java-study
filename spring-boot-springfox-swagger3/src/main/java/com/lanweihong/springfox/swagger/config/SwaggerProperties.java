package com.lanweihong.springfox.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 11:13
 */
@ConfigurationProperties(prefix = "spring.swagger")
@Data
public class SwaggerProperties {
    private Boolean enable;
    private String groupName;
    private String basePackage;
    private String version;
    private String title;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
}
