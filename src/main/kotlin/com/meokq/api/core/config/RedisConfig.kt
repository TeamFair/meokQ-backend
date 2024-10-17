package com.meokq.api.core.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
class RedisConfig(
    @Value("\${redis.endpoint}") private val host: String,
    @Value("\${redis.port}") private val port: Int,
    @Value("\${redis.password}") private val password: String,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = host
        redisStandaloneConfiguration.port = port
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