package com.meokq.api.core.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.APIKEY, `in` = SecuritySchemeIn.HEADER,
    name = "authorization", description = "Auth Token",
)
class SwaggerConfig {

    @Value("\${spring.profiles.active:local}")
    private lateinit var profile: String

    @Value("\${apiProject.version:V.0.0.0}")
    private lateinit var version: String

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
                    .title("[$profile] Meok-q Api Document")
                    .description("$profile 환경에서의 API 문서입니다.")
                    .version("$version")
            )
            .security(
                listOf(
                    SecurityRequirement()
                        .addList("authorization")
                )
            )
}