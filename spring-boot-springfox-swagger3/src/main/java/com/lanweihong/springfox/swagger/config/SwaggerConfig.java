package com.lanweihong.springfox.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 11:14
 */
@Configuration
@EnableOpenApi
@EnableConfigurationProperties(value = {SwaggerProperties.class})
@EnableSwagger2WebMvc
public class SwaggerConfig {

    SwaggerProperties swaggerProperties;

    @Autowired
    public void setSwaggerProperties(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    /**
     * 后台管理 API
     * @return
     */
    @Bean
    public Docket adminApi() {
        // OAS_30：区别于 V2，（OpenAPI Specification 的简称 OAS）
        return new Docket(
                // 使用 OpenAPI 3.0
                DocumentationType.OAS_30)
                .enable(swaggerProperties.getEnable())
                // API 信息
                .apiInfo(getApiInfo())
                // API 分组
                .groupName(swaggerProperties.getGroupName())
                .select()
                // 对某个包的接口进行监听
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                // 监听所有接口
                // .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 后台管理 API 信息
     * @return
     */
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title(swaggerProperties.getTitle())
                // 文档描述
                .description(swaggerProperties.getDescription())
                // 联系人信息
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                // 文档版本
                .version(swaggerProperties.getVersion())
                .build();
    }
}
