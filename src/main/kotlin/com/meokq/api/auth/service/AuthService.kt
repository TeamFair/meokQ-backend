package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.response.AuthResp
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Boss
import com.meokq.api.user.response.WithdrawResp
import com.meokq.api.user.service.AgreementService
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenService: JwtTokenService,
    private val bossService: BossService,
    private val customerService: CustomerService,
    private val agreementService: AgreementService,
) {
    fun login(req: LoginReq) : AuthResp {
        val response = AuthResp()

        // TODO : check token

        // register user data and get agreements
        if (req.userType == UserType.BOSS){
            try { // login
                val boss = bossService.findByEmail(req.email)
                req.userId = boss.bossId

                if (boss.status != UserStatus.ACTIVE)
                    throw InvalidRequestException("로그인 할수 없는 상태입니다. 관리자에게 문의하세요. (현재 상태:${boss.status.name})")

            }catch (e : NotFoundException){ // register
                val boss = bossService.saveModel(Boss(req))
                req.userId = boss.bossId
            }
        } else if (req.userType == UserType.CUSTOMER){
            try { // login
                val customer = customerService.findByEmail(req.email)
                req.userId = customer.customerId

                if (customer.status != UserStatus.ACTIVE)
                    throw InvalidRequestException("로그인 할수 없는 상태입니다. 관리자에게 문의하세요. (현재 상태:${customer.status.name})")

            }catch (e : NotFoundException){ // register
                val customer = customerService.save(req)
                req.userId = customer.customerId
            }
        } else if (req.userType == UserType.ADMIN) {
            // 미리 정해진 계정으로만 접근할수 있음.

        }

        // create jwt token
        response.authorization = jwtTokenService.generateToken(req)

        return response
    }

    fun logout(){
        // TODO : check token
        // TODO : delete token
    }

    fun withdraw(authReq: AuthReq) : WithdrawResp {
        // TODO : check token
        // TODO : unlink auth service
        // TODO : delete user data
        when (authReq.userType){
            UserType.BOSS -> {
                val user = bossService.findModelById(authReq.userId
                    ?:throw InvalidRequestException("사용자 아이디는 null 일 수 없습니다."))
                user.status = UserStatus.WITHDRAWN
                bossService.saveModel(user)
                return WithdrawResp(user)
            }
            UserType.CUSTOMER -> {
                val user = customerService.findModelById(authReq.userId
                    ?:throw InvalidRequestException("사용자 아이디는 null 일 수 없습니다."))
                user.status = UserStatus.WITHDRAWN
                customerService.saveModel(user)
                return WithdrawResp(user)
            }
            else -> {
                throw InvalidRequestException("탈퇴 할수 있는 사용자 유형이 아닙니다.")
            }
        }
    }
}