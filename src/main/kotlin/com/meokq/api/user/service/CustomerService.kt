package com.meokq.api.user.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.*
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.response.CustomerResp
import com.meokq.api.user.response.UserResp
import com.meokq.api.user.response.WithdrawResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val repository : CustomerRepository,
    private val challengeService: ChallengeService,
    //private val couponService: CouponService,
    // TODO : 개선필요/서비스에서는 서비스레이어만 호출하도록 설정
    private val couponRepository: CouponRepository,
): JpaService<Customer, String>, UserService{
    override var jpaRepository: JpaRepository<Customer, String> = repository

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

    fun update(authReq: AuthReq, request : CustomerUpdateReq){
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val model = findModelById(userId)
        if (model.nickname != request.nickname && repository.existsByNickname(request.nickname))
            throw NotUniqueException("nickname : ${request.nickname} is not unique.")

        model.nickname = request.nickname
        saveModel(model)
    }

    /**
     * user service Impl
     */
    override fun findByEmail(email: String): UserResp {
        val model = repository.findByEmail(email)
            ?: throw NotFoundException("customer is not found by email : $email")

        return UserResp(model)
    }

    override fun registerMember(req: LoginReq): UserResp {
        checkNotNullData(req.email, "saveCustomer : req.email이 없습니다.")

        val model = Customer(req)
        if (repository.existsByNickname(model.nickname!!))
            throw NotUniqueException("nickname : ${model.nickname} is not unique.")

        if (repository.existsByEmail(model.email!!))
            throw NotUniqueException("email : ${model.email} is not unique.")

        val result = saveModel(model)
        checkNotNullData(result.nickname, "saveCustomer : nickname이 없습니다.")

        return UserResp(result)
    }

    override fun withdrawMember(userId: String): WithdrawResp {
        try {
            val model = findModelById(userId)
            model.status = model.status.withdrawAction()
            val result = saveModel(model)
            return WithdrawResp(result)

        } catch (e: DataException){
            throw InvalidRequestException("존재하지 않는 사용자입니다.")
        }
    }
}