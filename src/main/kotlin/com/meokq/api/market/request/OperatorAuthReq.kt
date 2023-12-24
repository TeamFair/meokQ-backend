package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(name = "Operator-Auth-Request")
data class OperatorAuthReq(
    @Schema(description = "이름", example = "홍길동")
    val name: String,

    @Schema(description = "생년월일", example = "19900101")
    val birthdate: LocalDate,

    @Schema(description = "신분증 이미지 Id", example = "IM10000002")
    val idcardImageId: String,
)