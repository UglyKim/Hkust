package com.hkust.wmsc.config;

import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile({"dev", "test"})
public class OpenApiConfig {
    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI hkustOpenAPI() {
        return new OpenAPI()
                .tags(Arrays.asList(
                        new Tag().name("认证").description("登陆登出"),
                        new Tag().name("用户").description("用户相关操作"),
                        new Tag().name("统计").description("统计相关操作"),
                        new Tag().name("智能仓储柜").description("智能仓储柜相关操作"),
                        new Tag().name("试剂").description("试剂相关操作"),
                        new Tag().name("操作日志").description("操作日志"),
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
                .info(new Info().title("HKUST WMSC API")
                        .description("仓储智能柜")
                        .version("v1.0.0")
                        .license(new License()));
    }

    @Bean
    public GroupedOpenApi wmscApi() {
        return GroupedOpenApi.builder()
                .group("仓储智能柜")
//                .packagesToScan("com.hkust.wmsc.controller")
                .packagesToScan("com.hkust")
//                .pathsToMatch("/cabinet/**")
                .build();
    }
}
