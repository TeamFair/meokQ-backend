package com.meokq.api.user.service

import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.user.model.Boss
import com.meokq.api.user.repository.BossRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class BossService (
    val repository: BossRepository
) : JpaService<Boss, String> {
    override var jpaRepository: JpaRepository<Boss, String> = repository

    fun findByEmail(email: String): Boss {
        return repository.findByEmail(email)
            ?: throw NotFoundException("customer is not found by email : $email")
    }

    fun save(req: LoginReq): Boss {
        val model = Boss(req)

        if (repository.existsByEmail(model.email!!))
            throw NotUniqueException("email : ${model.email} is not unique.")

        return saveModel(model)
    }
}
