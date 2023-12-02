package com.meokq.api.user.response

import com.meokq.api.core.enums.TypeYN
import com.meokq.api.user.enums.AgreementType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Agreement-Response")
data class AgreementResp(
    @Schema(description = "The type of agreement (e.g., Privacy Policy, Marketing Consent).")
    val agreementType: AgreementType?,

    @Schema(description = "The version of the agreement.")
    val version: Int?,

    @Schema(description = "Indicates whether the agreement has been accepted (YES or NO).")
    val acceptYn: TypeYN?,

    @Schema(description = "The date and time when the agreement was accepted.")
    val acceptDate: String?,
)
