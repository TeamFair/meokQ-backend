package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.market.enums.MarketAuthResult
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
    var marketId: String? = null, // Market model 의 id 와 연결되는 외부 키
    @Enumerated(EnumType.STRING)
    var reviewResult: MarketAuthResult? = null,
    var comment: String? = null,

    // 영업신고증 정보
    var licenseId : String? = null,
    var licenseImageId : String? = null, // Image model 의 id 와 연결되는 외부 키
    var ownerNameOnLicense : String? = null, //영업신고증상 대표자명
    var marketName : String? = null, //상호명
    var address : String? = null,
    var postalCode : String? = null,

    // 사업자 개인정보
    val ownerName: String? = null,
    val ownerBirthdate: LocalDate? = null,
    val idCardImageId: String? = null // Image model 의 id 와 연결되는 외부 키
) : BaseModel()