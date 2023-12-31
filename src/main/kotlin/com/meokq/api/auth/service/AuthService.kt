package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.response.AuthResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.user.model.Customer
import com.meokq.api.user.request.BossReq
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

            }catch (e : NotFoundException){ // register
                val boss = bossService.save(
                    BossReq(email = req.email)
                )
                req.userId = boss.bossId
            }
        } else if (req.userType == UserType.CUSTOMER){
            try { // login
                val customer = customerService.findByEmail(req.email)
                req.userId = customer.customerId

            }catch (e : NotFoundException){ // register
                val customer = customerService.saveModel(Customer(req))
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

    fun withdraw(){
        // TODO : check token
        // TODO : unlink auth service
        // TODO : delete user data
    }
}