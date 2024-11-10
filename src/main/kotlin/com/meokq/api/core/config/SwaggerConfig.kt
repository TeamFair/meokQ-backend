package com.meokq.api.core.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.net.InetAddress

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.APIKEY, `in` = SecuritySchemeIn.HEADER,
    name = "authorization", description = "Auth Token",
)
class SwaggerConfig(
    private val environment: Environment
) {
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
    fun openApi(): OpenAPI {
        // set profile data
        val profile: String = environment.getProperty("spring.profiles.active", "local")
        var host = InetAddress.getLocalHost().hostAddress
        val port: String = environment.getProperty("ec2.$profile.port", "8080")
        val version: String = environment.getProperty("apiProject.version", "V.0.0.0")

        // set server data
        val server = Server()
        server.url = "http://$host:$port"
        return OpenAPI().servers(listOf(server))
            .info(
                Info()
                    .title("[$profile] Ilsang Api Document")
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

}