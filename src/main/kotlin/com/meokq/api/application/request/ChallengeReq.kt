package com.meokq.api.application.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Challenge-Request")
class ChallengeReq(
    @Schema(description = "quest ID")
    val questId : String?,

    @Schema(description = "고객 ID(로그인 구현 후 제거 대상)")
    val customerId : String?,   // TODO : 추후 제거

    @Schema(description = "영수증 이미지의 주소")
    val receiptImage : String?
)