package com.hkust.wmc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile({"dev","test"})
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI hkustOpenAPI() {
        return new OpenAPI()
                .tags(Arrays.asList(
                        new Tag().name("认证").description("登陆登出"),
                        new Tag().name("首页").description("首页"),
                        new Tag().name("用户").description("用户相关操作"),
                        new Tag().name("智能仓储柜").description("智能仓储柜相关操作"),
                        new Tag().name("试剂").description("试剂相关操作"),
                        new Tag().name("日志").description("日志相关操作"),
                        new Tag().name("录像").description("录像相关操作")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .info(new Info().title("HKUST WMC API")
                        .description("仓储管理控制台")
                        .version("v1.0.0")
                        .license(new License()));
    }

    @Bean
    public GroupedOpenApi wmscApi() {
        return GroupedOpenApi.builder()
                .group("仓储管理控制台")
//                .packagesToScan("com.hkust.wmc.controller")
                .packagesToScan("com.hkust")
//                .pathsToMatch("/cabinet/**")
                .build();
    }
}
