package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.market.enums.MarketStatus
import com.meokq.api.market.request.MarketReq
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_market")
class Market(
    @Id
    /*@GenericGenerator(
        name = "SEQ_GEN_MARKET",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "MARKET_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MARKET")*/
    @UuidGenerator
    var marketId : String? = null,

    var name : String? = null,
    var address : String? = null,
    var district : String? = null,
    var content : String? = null,
    var phone : String? = null,
    var ticketCount : Long = 10, // TODO : 초기값은 10으로 세팅
    var logoImageId : String? = null, // Image model 의 id 와 연결되는 외부 키

    @Enumerated(EnumType.STRING)
    var status : MarketStatus = MarketStatus.UNDER_REVIEW,

    var presidentId : String? = null, // Boss model 의 id 와 연결되는 외부 키
) : BaseModel(){
    constructor(req : MarketReq, bossId : String) : this(
        name = req.name,
        address = req.address,
        phone = req.phone,
        district = req.district,
        logoImageId = req.logoImageId,
        presidentId = bossId,
    )
}