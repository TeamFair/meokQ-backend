package com.meokq.api.core.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.APIKEY, `in` = SecuritySchemeIn.HEADER,
    name = "authorization", description = "Auth Token",
)
class SwaggerConfig {

    @Bean
    fun openResourceApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("open-resource")
            .pathsToMatch("/api/open/**")
            .build()

    @Bean
    fun bossResourceApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("boss-resource")
            .pathsToMatch("/api/boss/**")
            .build()

    @Bean
    fun adminResourceApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("admin-resource")
            .pathsToMatch("/api/admin/**")
            .build()

    @Bean
    fun customerResourceApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("customer-resource")
            .pathsToMatch("/api/customer/**")
            .build()

    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Meok-q Api Document")
                    //.description("")
                    .version("v2")
            )
            .security(
                listOf(
                    SecurityRequirement()
                        .addList("authorization")
                )
            )
}