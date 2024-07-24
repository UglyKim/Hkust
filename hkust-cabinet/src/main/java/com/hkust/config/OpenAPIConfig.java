package com.hkust.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebMvc
//@Profile({"dev","test"})
public class OpenAPIConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI hkustOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .info(new Info().title("HKUST API")
                        .description("港大智能柜项目API")
                        .version("v1.0.0")
                        .license(new License()));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("鉴权")
                .packagesToScan("com.hkust.controller.auth")
//                .pathsToMatch("/manage/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cabinetApi() {
        return GroupedOpenApi.builder()
                .group("智能柜终端")
                .packagesToScan("com.hkust.controller.cabinet")
//                .pathsToMatch("/CabinetVO/**")
                .build();
    }

    @Bean
    public GroupedOpenApi manageApi() {
        return GroupedOpenApi.builder()
                .group("管理控制台")
                .packagesToScan("com.hkust.controller.management")
//                .pathsToMatch("/manage/**")
                .build();
    }

}