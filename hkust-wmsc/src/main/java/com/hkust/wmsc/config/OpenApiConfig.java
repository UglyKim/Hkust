package com.hkust.wmsc.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.info.Contact;
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
import java.util.List;

@EnableKnife4j

@Configuration
@Profile({"dev", "test"})
public class OpenApiConfig {

    private static final String BEARER_AUTH = "bearerAuth";
    private static final String API_TITLE = "HKUST WMSC API";
    private static final String API_DESCRIPTION = "仓储智能柜";
    private static final String API_VERSION = "v1.0.0";
    private static final String TERMS_OF_SERVICE = "1.用本 API 服务即表示您同意遵守以下条款和条件。<br>" +
            "2.我们授予您使用本 API 的有限、不可转让的授权，用于合法的、非商业性目的。<br>" +
            "3.您的数据将严格保密，我们不会将其用于任何未经授权的用途。<br>" +
            "4.保留随时修改 API 功能、更新文档或终止服务的权利。任何修改将提前通知您。<br>" +
            "5.本API是按“现状”提供的。我们不对因使用 API 造成的任何间接损失负责。<br>" +
            "6.可能会不时修改这些条款。";

    @Bean
    public OpenAPI hkustOpenAPI() {
        return new OpenAPI()
                .tags(getApiTags())  // 使用提取的方法
                .components(new Components().addSecuritySchemes(BEARER_AUTH, createBearerSecurityItem())) // 组合并创建Bearer认证
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH)) // 绑定认证方式
                .info(createApiInfo()); // 使用提取的方法生成API信息
    }

    // 提取 tags 配置
    private List<Tag> getApiTags() {
        return Arrays.asList(
                new Tag().name("认证").description("登陆登出"),
                new Tag().name("用户").description("用户相关操作"),
                new Tag().name("统计").description("统计相关操作"),
                new Tag().name("智能仓储柜").description("智能仓储柜相关操作"),
                new Tag().name("试剂").description("试剂相关操作"),
                new Tag().name("操作日志").description("操作日志"),
                new Tag().name("录像").description("录像相关操作")
        );
    }

    // 提取 API 信息配置
    private Info createApiInfo() {
        return new Info()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .termsOfService(TERMS_OF_SERVICE)
                .version(API_VERSION)
                .license(new License())
                .contact(new Contact().name("HKUST Team"));
    }

    // 提取 Bearer 认证配置
    private SecurityScheme createBearerSecurityItem() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("请输入 Bearer Token");
    }

    @Bean
    public GroupedOpenApi wmscApi() {
        return GroupedOpenApi.builder()
                .group("仓储智能柜")
                .packagesToScan("com.hkust")
                .build();
    }
}
