package com.meokq.api.user.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.core.exception.TokenException
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.response.CustomerResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repository : CustomerRepository,
    private val challengeService: ChallengeService,
    //private val couponService: CouponService,
    // TODO : 개선필요/서비스에서는 서비스레이어만 호출하도록 설
    private val couponRepository: CouponRepository,
): JpaService<Customer, String>{
    override var jpaRepository: JpaRepository<Customer, String> = repository

    fun findByEmail(email : String): Customer {
        return repository.findByEmail(email)
            ?: throw NotFoundException("customer is not found by email : $email")
    }

    fun findByAuthReq(authReq: AuthReq): CustomerResp{
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val model = findModelById(userId)
        val challengeCount = challengeService.count(
            ChallengeSearchDto(
                userId = userId,
                status = ChallengeStatus.APPROVED
            )
        )

        // TODO : 개선필요/customerService와 couponService간 순환참조 오류로 레파지토리를 직접 호출
        val couponCount = couponRepository.countByUserIdAndStatus(
            status = CouponStatus.ISSUED, userId = userId
        )

        return CustomerResp(model = model, challengeCount=challengeCount, couponCount = couponCount)
    }

    fun save(req: LoginReq): Customer {
        val model = Customer(req)

        checkNotNullData(model.nickname, "saveCustomer : ${model.nickname}이 없습니다.")
        checkNotNullData(model.email, "saveCustomer : ${model.email}이 없습니다.")

        if (repository.existsByNickname(model.nickname!!))
            throw NotUniqueException("nickname : ${model.nickname} is not unique.")

        if (repository.existsByEmail(model.email!!))
            throw NotUniqueException("email : ${model.email} is not unique.")

        return saveModel(model)
    }

    fun update(authReq: AuthReq, request : CustomerUpdateReq){
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val model = findModelById(userId)
        if (model.nickname != request.nickname && repository.existsByNickname(request.nickname))
            throw NotUniqueException("nickname : ${request.nickname} is not unique.")

        model.nickname = request.nickname
        saveModel(model)
    }
}