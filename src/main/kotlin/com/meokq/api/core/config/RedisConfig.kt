package com.meokq.api.core.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableCaching
@Configuration
class RedisConfig {
    private val host : String = "localhost"
    private val port : String = "6379"
    //private val password : String = "test"

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = host
        redisStandaloneConfiguration.port = port.toInt()
        //redisStandaloneConfiguration.setPassword(password)
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String?, Any?>? {
        val redisTemplate: RedisTemplate<String?, Any?> = RedisTemplate()
        redisTemplate.setConnectionFactory(redisConnectionFactory()!!)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }
}