package com.meokq.api.agreement.response

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.agreement.enums.AgreementType
import com.meokq.api.agreement.model.Agreement
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
) {
    constructor(model: Agreement) : this(
        agreementType = model.agreementType,
        version = model.version,
        acceptYn = model.acceptYn,
        acceptDate = model.createDate?.let { DateTimeConverterV2.convertToString(it) }
    )
}
