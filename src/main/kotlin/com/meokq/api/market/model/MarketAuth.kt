package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.market.enums.MarketAuthResult
import com.meokq.api.market.request.MarketAuthReq
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_market_auth_history")
data class MarketAuth (
    @Id
    @UuidGenerator
    var recordId : String? = null,
    var marketId: String? = null, // Market model 의 id 와 연결되는 외부 키
    @Enumerated(EnumType.STRING)
    var reviewResult: MarketAuthResult? = null,
    var comment: String? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "operator_info_id")
    var operatorAuth: OperatorAuth? = null,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "license_info_id")
    var licenseAuth: LicenseAuth? = null,
) : BaseModel(){

    constructor(request: MarketAuthReq): this(
        marketId = request.marketId,
        reviewResult = null,
        comment = null,
        operatorAuth = OperatorAuth(request.owner),
        licenseAuth = LicenseAuth(request.license)
    )
}