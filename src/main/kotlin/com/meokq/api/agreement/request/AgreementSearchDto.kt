package com.meokq.api.agreement.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.meokq.api.agreement.enums.AgreementType

data class AgreementSearchDto(
    @JsonIgnore
    var userId : String? = null,
    //TODO : 추후 개선
    //@Schema(example = "2020-12-01 00:00:00")
    //val creatDateFrom : String? = null,
    //@Schema(example = "2025-12-01 00:00:00")
    //val creatDateTo : String? = null,
    val agreementType : AgreementType? = null
)
