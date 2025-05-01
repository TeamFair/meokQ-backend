package com.meokq.api.user.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.*
import com.meokq.api.coupon.enums.CouponStatus
import com.meokq.api.coupon.repository.CouponRepository
import com.meokq.api.file.service.ImageService
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.repository.queryDSL.CustomerQueryDSLRepository
import com.meokq.api.user.request.CustomerUpdateProfileReq
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.response.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CustomerService(
    private val repository : CustomerRepository,
    private val challengeRepository: ChallengeRepository,
    //private val couponService: CouponService,
    // TODO : 개선필요/서비스에서는 서비스레이어만 호출하도록 설정
    private val couponRepository: CouponRepository,
    private val customerQueryDSLRepository: CustomerQueryDSLRepository,
    private val imageService: ImageService,
): JpaService<Customer, String>, UserService{
    override var jpaRepository: JpaRepository<Customer, String> = repository

    @Transactional(readOnly = true)
    fun findByAuthReq(authReq: AuthReq): CustomerResp{
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val model = findModelById(userId)
        val challengeCount = challengeRepository.countByCustomerIdAndStatus(userId, ChallengeStatus.APPROVED)

        // TODO : 개선필요/customerService와 couponService간 순환참조 오류로 레파지토리를 직접 호출
        val couponCount = couponRepository.countByUserIdAndStatus(
            status = CouponStatus.ISSUED, userId = userId
        )

        return CustomerResp(model = model, challengeCount= challengeCount, couponCount = couponCount)
    }

    @Transactional
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

    @Transactional
    override fun registerMember(req: LoginReq): UserResp {
        checkNotNullData(req.email, "saveCustomer : req.email이 없습니다.")

        val model = Customer(req)

        // generate nickname
        val currentMaxEntity = repository.findTopByOrderByNicknameSeqDesc()
        model.nicknameSeq = (currentMaxEntity?.nicknameSeq ?: 0) + 1
        model.nickname = "일상${String.format("%08d", model.nicknameSeq)}"

        if (repository.existsByEmail(model.email!!))
            throw NotUniqueException("email : ${model.email} is not unique.")

        val result = saveModel(model)
        checkNotNullData(result.nickname, "saveCustomer : nickname이 없습니다.")

        return UserResp(result)
    }

    @Transactional
    override fun withdrawMember(userId: String): WithdrawResp {
        try {
            val model = findModelById(userId)
            this.challengeRepository.deleteByCustomerId(model.customerId!!)
            this.repository.delete(model)

            return WithdrawResp(
                email = null,
                status = UserStatus.WITHDRAW,
                channel = null,
            )

        } catch (e: DataException){
            throw InvalidRequestException("존재하지 않는 사용자입니다.")
        }
    }

    fun getRankForXp(rankSearchCondition: RankSearchCondition): List<XpRankCustomerResp> {
        return customerQueryDSLRepository.getXpRanking(rankSearchCondition)
    }

    fun getTopUsersByXp(limit: Long): List<CustomerXpLankResp> {
        return customerQueryDSLRepository.getTopUsersByXp(limit)
    }

    @Transactional
    fun deleteProfileImage(authReq: AuthReq) {
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val user = this.findModelById(userId)
        val profileImage = user.profileImageId ?: throw InvalidRequestException("프로필이 존재하지 않습니다.")

        user.deleteProfileImage()
        imageService.deleteById(profileImage, authReq)
    }

    @Transactional
    fun updateProfileImage(authReq: AuthReq, request : CustomerUpdateProfileReq) {
        val userId = authReq.userId ?: throw TokenException("사용자 아이디가 없습니다.")
        val user = this.findModelById(userId)

        user.updateProfileImage(request.imageId)
    }

}
