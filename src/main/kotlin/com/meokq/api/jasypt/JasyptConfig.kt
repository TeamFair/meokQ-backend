package com.meokq.api.jasypt

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableEncryptableProperties
class JasyptConfig{

    @Autowired
    private lateinit var properties: JasyptProperties

    @Bean
    fun stringEncryptor(): StandardPBEStringEncryptor {
        val encryptor = StandardPBEStringEncryptor()
        val config = getConfig()
        encryptor.setConfig(config)

        return encryptor
    }

    private fun getConfig(): SimpleStringPBEConfig {
        val config = SimpleStringPBEConfig()
        with(properties){
            config.password = password
            config.algorithm = algorithm
            config.setKeyObtentionIterations(keyObtentionIterations)
            config.setPoolSize(poolSize)
            config.providerName = providerName
            config.setSaltGeneratorClassName(saltGeneratorClassName)
            config.setIvGeneratorClassName(ivGeneratorClassName)
            config.stringOutputType = stringOutputType
        }
        return config
    }
}