package com.lanweihong.springdoc.swagger.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置类
 * 注解 @OpenAPIDefinition： 设置全局的 API 文档信息
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 16:09
 */
@OpenAPIDefinition(
        // 定义标签
        tags = {
            @Tag(name = "用户管理", description = "用户模块操作"),
            @Tag(name = "角色管理", description = "角色模块操作")
        },
        // 文档信息
        info = @Info(
            title = "洪伟商城接口 API 文档",
            description = "用户数据管理......",
            version = "1.0.0",
            contact = @Contact(name = "lanweihong", email = "986310747@qq.com", url = "https://www.lanweihong.com"),
            license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
        ),
        // 目标服务器
        servers = {
            @Server(description = "生产环境服务器", url = "https://xxxx.com/api/v1"),
            @Server(description = "测试环境服务器", url = "https://test.xxxx.com/api/v1")
        },
        security = @SecurityRequirement(name = "Oauth2"),
        // 外部文档
        externalDocs = @ExternalDocumentation(
                description = "项目编译部署说明",
                url = "http://localhost/deploy/README.md"
        )
)
@Configuration
public class SwaggerConfig {

        @Bean
        public GroupedOpenApi userApi() {
                return GroupedOpenApi.builder()
                        .group("User")
                        // 指定路径
                        .pathsToMatch("/api/users/**")
                        // 指定特定的 API 文档信息
                        .addOpenApiCustomiser(userApiCustomiser())
                        .build();
        }

        @Bean
        public GroupedOpenApi roleApi() {
                return GroupedOpenApi.builder()
                        .group("Role")
                        // 指定扫描包
                        .packagesToScan("com.lanweihong.springdoc.swagger.controller.role")
                        .pathsToMatch("/api/roles/**")
                        .build();
        }

        /**
         * 定义 OpenApiCustomiser ，用于指定的 group
         * 用于可参考上面写的 @OpenAPIDefinition 内容
         * @return
         */
        public OpenApiCustomiser userApiCustomiser() {
                return openApi -> openApi.info(new io.swagger.v3.oas.models.info.Info()
                .title("用户管理 API 文档")
                        .description("实现对用户数据的增删改查等操作"));
        }
}
