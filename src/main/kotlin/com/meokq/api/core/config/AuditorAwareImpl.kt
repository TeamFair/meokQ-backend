package com.meokq.api.core.config

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.exception.TokenException
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import java.util.*

@Configuration
class AuditorAwareImpl : AuditorAware<String>, AuthDataProvider {
    override fun getCurrentAuditor(): Optional<String> {
        return try {
            val authReq = getAuthReq()
            Optional.ofNullable(authReq.userId)
        } catch (e : TokenException){
            Optional.ofNullable(UserType.UNKNOWN.name)
        }
    }
}