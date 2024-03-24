package com.meokq.api.jasypt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JasyptProperties (
    @Value("\${jasypt.encryptor.password}") val password: String,
    @Value("\${jasypt.encryptor.algorithm}") val algorithm: String,
    @Value("\${jasypt.encryptor.keyObtentionIterations}") val keyObtentionIterations: String,
    @Value("\${jasypt.encryptor.poolSize}") val poolSize: String,
    @Value("\${jasypt.encryptor.providerName}") val providerName: String,
    @Value("\${jasypt.encryptor.saltGeneratorClassName}") val saltGeneratorClassName: String,
    @Value("\${jasypt.encryptor.ivGeneratorClassName}") val ivGeneratorClassName: String,
    @Value("\${jasypt.encryptor.stringOutputType}") val stringOutputType: String
)
