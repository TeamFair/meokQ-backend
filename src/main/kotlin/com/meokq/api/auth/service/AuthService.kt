package com.meokq.api.auth.service

import com.meokq.api.auth.dto.OAuthProperties
import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.enums.UserType.*
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.request.OAuthLoginRequest
import com.meokq.api.auth.request.OAuthRefreshRequest
import com.meokq.api.auth.response.AuthResp
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.redis.RedisTokenService
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp
import com.meokq.api.user.service.AdminService
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import com.meokq.api.user.service.UserService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenService: JwtTokenService,
    private val bossService: BossService,
    private val customerService: CustomerService,
    private val adminService: AdminService,
    private val redisTokenService: RedisTokenService,
    private val oauthProviderFactory: OAuthProviderFactory,
    private val oAuthProperties: OAuthProperties,
) {
    fun login(req: LoginReq): AuthResp {
        val userService = getUserService(req.userType)
        var user: UserResp? = null
        try { // login
            user = userService.findByEmail(req.email)
            if (user.status != UserStatus.ACTIVE)
                throw InvalidRequestException("로그인 할수 없는 상태입니다. 관리자에게 문의하세요. (현재 상태:${user.status.name})")

        } catch (e: NotFoundException) { // register
            user = userService.registerMember(req)
        }

        // create jwt token
        checkNotNullData(user, "사용자 정보가 존재하지 않습니다.")
        checkNotNullData(user!!.userId, "사용자 아이디가 존재하지 않습니다.")
        val authReqForToken = AuthReq(user, req.userType)
        val token = jwtTokenService.generateToken(authReqForToken)
        val refreshToken = jwtTokenService.generateToken(authReqForToken)

        // save token to redis
        redisTokenService.saveToken(user.userId!!, token, refreshToken)

        return AuthResp(authorization = token, refreshToken = refreshToken)
    }

    fun login(request: OAuthLoginRequest): AuthResp {
        val clientId = oAuthProperties.getClientIdForProvider(request.provider, request.osType)

        val verifier = oauthProviderFactory.getVerifier(request.provider)
        val claims = verifier.verifyToken(request.idToken, clientId)

        val email = claims.email

        return this.login(
            LoginReq(
                userType = CUSTOMER,
                email = email!!,
                channel = request.provider,
                accessToken = "",
                refreshToken = "",
            )
        )

    }

    fun refresh(request: OAuthRefreshRequest): AuthResp {
        val authReq = this.jwtTokenService.convertToRequest(request.accessToken)
        val userService = getUserService(authReq.userType)
        val user = userService.findById(authReq.userId!!)

        val accessToken = this.redisTokenService.getToken(authReq.userId)
        val refreshToken = this.redisTokenService.getRefreshToken(authReq.userId)

        if (accessToken == request.accessToken && refreshToken == request.refreshToken) {
            return this.login(
                LoginReq(
                    userType = CUSTOMER,
                    email = user?.email!!,
                    channel = AuthChannel.REFRESH,
                    accessToken = "",
                    refreshToken = "",
                )
            )
        }

        throw InvalidRequestException("유효하지 않은 Token 정보입니다.")
    }

    fun logout(authReq: AuthReq) {
        checkNotNullData(authReq.userId, "사용자 아이디가 존재하지 않습니다.")
        redisTokenService.deleteToken(authReq.userId!!)
    }

    fun withdraw(authReq: AuthReq): WithdrawResp {
        this.logout(authReq)

        // change user status : DORMANT 휴면 계정
        val userService = getUserService(authReq.userType)
        return userService.withdrawMember(
            authReq.userId
                ?: throw InvalidRequestException("사용자 아이디는 null 일 수 없습니다.")
        )
    }

    fun isTokenValid(userId: String, token: String): Boolean {
        val storedToken = redisTokenService.getToken(userId)
        return storedToken == token
    }

    private fun getUserService(userType: UserType): UserService {
        return when (userType) {
            BOSS -> return bossService
            CUSTOMER -> return customerService
            ADMIN -> return adminService
            else -> {
                throw InvalidRequestException("지원하지 않는 사용자 유형입니다.")
            }
        }
    }
}
