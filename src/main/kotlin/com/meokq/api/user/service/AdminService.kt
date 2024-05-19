package com.meokq.api.user.service

import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AdminService : UserService {
    @Value("\${matq.admin.email:admin}")
    private lateinit var adminEmail: String

    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    /**
     * user service Impl
     */
    override fun findByEmail(email: String): UserResp {
        // FIXME : admin 사용자는 특정 계정으로만 로그인할수 있도록 지정 함. (임시)
        if (email != adminEmail)
            throw InvalidRequestException("admin 사용자는 이미 존재하는 계정으로만 로그인할수 있습니다. 관리자에게 문의해주세요.")

        return UserResp(
            userId = adminId,
            status = UserStatus.ACTIVE,
        )
    }

    override fun registerMember(req: LoginReq): UserResp {
        throw InvalidRequestException("현재 지원하지 않는 기능입니다. 관리자에게 문의해주세요.")
    }

    override fun withdrawMember(userId: String): WithdrawResp {
        throw InvalidRequestException("현재 지원하지 않는 기능입니다. 관리자에게 문의해주세요.")
    }

    override fun exit(userId: String): Boolean {
        return userId == adminId
    }
}