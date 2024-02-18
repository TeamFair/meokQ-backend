package com.meokq.api.agreement.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.agreement.enums.AgreementType

data class AgreementReq(
    val agreementType: AgreementType,
    val version : Int,
    val acceptYn: TypeYN,
) {

    @JsonIgnore
    var userId : String? = null
}