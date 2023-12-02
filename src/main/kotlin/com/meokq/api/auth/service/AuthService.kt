package com.meokq.api.auth.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.auth.response.AuthResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.user.request.BossReq
import com.meokq.api.user.service.AgreementService
import com.meokq.api.user.service.BossService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val bossService: BossService,
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
        }

        // create jwt token

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