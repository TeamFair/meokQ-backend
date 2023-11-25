package com.meokq.api.application.model

import com.meokq.api.application.enums.ReviewResult
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate

@Entity(name = "tb_market_auth_history")
data class MarketAuth (
    @Id
    @UuidGenerator
    var recordId : String? = null,
    var marketId: String? = null,
    @Enumerated(EnumType.STRING)
    val reviewResult: ReviewResult = ReviewResult.PENDING,
    val comment: String? = null,

    // 영업신고증 정보
    var licenseId : String? = null,
    var licenseImageId : String? = null,
    var ownerNameOnLicense : String? = null, //영업신고증상 대표자명
    var marketName : String? = null, //상호명
    var address : String? = null,
    var postalCode : String? = null,

    // 사업자 개인정보
    val ownerName: String? = null,
    val ownerBirthdate: LocalDate? = null,
    val idCardImageId: String? = null
) : BaseModel()