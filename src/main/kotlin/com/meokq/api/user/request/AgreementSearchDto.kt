package com.meokq.api.user.request

import com.meokq.api.user.enums.AgreementType
import java.time.LocalDateTime

data class AgreementSearchDto(
    var userId : String?,
    val creatDateFrom : LocalDateTime? = null,
    val creatDateTo : LocalDateTime? = null,
    val agreementType : AgreementType? = null
)
