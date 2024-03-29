package com.meokq.api.core.config

import com.meokq.api.auth.filters.JwtFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecureConfig(
    private val jwtFilter: JwtFilter,
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.authorizeHttpRequests()
            //.requestMatchers("/api/**").authenticated()
            //.requestMatchers("/auth/**").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
//            .requestMatchers("/api/admin").hasRole("ADMIN")
//            .requestMatchers("/api/boss").hasRole("BOSS")
//            .requestMatchers("/api/customer").hasRole("CUSTOMER")
            .anyRequest().permitAll()
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}