package com.meokq.api.core.config

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment


@Configuration
@EnableEncryptableProperties
class JasyptConfig(
    private var environment: Environment,
) {

    @Bean
    fun jasyptStringEncryptor(): StandardPBEStringEncryptor {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        val config = SimpleStringPBEConfig()
        config.password = getPassword()
        config.algorithm = "PBEWithMD5AndDES"
        config.setKeyObtentionIterations("1000")
        config.setPoolSize("1")
        config.providerName = "SunJCE"
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator")
        config.stringOutputType = "base64"
        encryptor.setConfig(config)

        return encryptor
    }

    private fun getPassword(): String? {
        return environment.getProperty("jasypt.encryptor.password")
    }
}