package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.enums.UserType.*
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.response.AuthResp
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp
import com.meokq.api.user.service.AdminService
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import com.meokq.api.user.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenService: JwtTokenService,
    private val bossService: BossService,
    private val customerService: CustomerService,
    private val adminService: AdminService,
) {

    fun login(req: LoginReq): AuthResp {
        // TODO : check token

        // register user data
        val userService = getUserService(req.userType)
        var user: UserResp? = null
        try { // login
            user = userService.findByEmail(req.email)
            if (user.status != UserStatus.ACTIVE)
                throw InvalidRequestException("로그인 할수 없는 상태입니다. 관리자에게 문의하세요. (현재 상태:${user.status.name})")

        } catch (e: NotFoundException){ // register
            user = userService.registerMember(req)
        }

        // create jwt token
        checkNotNullData(user, "사용자 정보가 존재하지 않습니다.")
        checkNotNullData(user!!.userId, "사용자 아이디가 존재하지 않습니다.")
        val authReqForToken = AuthReq(user, req.userType)
        val token = jwtTokenService.generateToken(authReqForToken)

        //TODO Spring security 기능 추가 필요
//        val authorities = mutableListOf<GrantedAuthority>()
//        when(req.userType){
//            ADMIN -> authorities.add(SimpleGrantedAuthority("ADMIN"))
//            BOSS -> authorities.add(SimpleGrantedAuthority("BOSS"))
//            CUSTOMER -> authorities.add(SimpleGrantedAuthority("CUSTOMER"))
//            UNKNOWN -> authorities.add(SimpleGrantedAuthority("OPEN")) }
        return AuthResp(authorization = token)
    }

    fun logout(){
        // TODO : check token
        // TODO : delete token
    }

    fun withdraw(authReq: AuthReq): WithdrawResp {
        // TODO : check token
        // TODO : unlink auth service

        // change user status : DORMANT 휴면 계정
        val userService = getUserService(authReq.userType)
        return userService.withdrawMember(authReq.userId
            ?:throw InvalidRequestException("사용자 아이디는 null 일 수 없습니다."))
    }

    private fun getUserService(userType: UserType): UserService{
        return when (userType){
            BOSS -> return bossService
            CUSTOMER -> return customerService
            ADMIN -> return adminService
            else -> {throw InvalidRequestException("지원하지 않는 사용자 유형입니다.")}
        }
    }
}