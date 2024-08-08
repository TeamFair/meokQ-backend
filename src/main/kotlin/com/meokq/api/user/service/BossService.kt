package com.meokq.api.user.service

import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.DataException
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.user.model.Boss
import com.meokq.api.user.repository.BossRepository
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BossService (
    val repository: BossRepository
) : JpaService<Boss, String>, UserService {
    override var jpaRepository: JpaRepository<Boss, String> = repository

    /**
     * user service Impl
     */
    override fun findByEmail(email: String): UserResp {
        val model = repository.findByEmail(email)
            ?: throw NotFoundException("boss is not found by email : $email")

        return UserResp(model)
    }

    override fun registerMember(req: LoginReq): UserResp {
        if (repository.existsByEmail(req.email))
            throw NotUniqueException("email : ${req.email} is not unique.")

        val model = Boss(req)
        val result = saveModel(model)
        return UserResp(result)
    }

    override fun withdrawMember(userId: String): WithdrawResp {
        try {
            val model = findModelById(userId)
            model.status = model.status.withdrawAction()
            model.withdrawnAt = LocalDateTime.now()
            val result = saveModel(model)
            return WithdrawResp(result)

        } catch (e: DataException){
            throw InvalidRequestException("존재하지 않는 사용자입니다.")
        }
    }
}
