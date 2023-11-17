package com.meokq.api.core.config

import jakarta.servlet.MultipartConfigElement
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import org.springframework.web.multipart.MultipartResolver
import org.springframework.web.multipart.support.StandardServletMultipartResolver


@Configuration
class MultiPartConfig(
    @Value("\${multipart.max-upload-size}") private val maxUploadSize: DataSize,
) {
    @Bean
    fun multipartResolver(): MultipartResolver? {
        return StandardServletMultipartResolver()
    }

    @Bean
    fun multipartConfigElement(): MultipartConfigElement? {
        val factory = MultipartConfigFactory()
        factory.setMaxRequestSize(maxUploadSize)
        factory.setMaxFileSize(maxUploadSize)
        return factory.createMultipartConfig()
    }

}