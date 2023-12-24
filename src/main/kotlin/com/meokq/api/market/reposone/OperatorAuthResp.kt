package com.meokq.api.market.reposone

import com.meokq.api.image.response.ImageResp
import com.meokq.api.market.model.OperatorAuth
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
@Schema(name = "Operator-Info-Response")
data class OperatorAuthResp(
    @Schema(description = "이름")
    val name: String?,

    @Schema(description = "생년월일")
    val birthdate: LocalDate?,

    @Schema(description = "신분증 이미지")
    val idcardImage: ImageResp?,
) {
    constructor(model : OperatorAuth) : this(
        name = model.ownerName,
        birthdate = model.ownerBirthdate,
        idcardImage = model.idcardImage?.let { ImageResp(it) },
    )
}