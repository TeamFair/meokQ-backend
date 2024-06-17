package com.meokq.api.user.service

import com.meokq.api.auth.request.LoginReq
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp

interface UserService{
    fun findByEmail(email: String): UserResp
    fun registerMember(req: LoginReq): UserResp
    fun withdrawMember(userId: String): WithdrawResp

    fun exit(userId: String): Boolean {return false}
}