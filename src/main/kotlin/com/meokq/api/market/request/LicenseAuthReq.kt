package com.meokq.api.market.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Operating-License-Request",
    description = "영업신고증 정보"
)
data class LicenseAuthReq(
    @Schema(description = "영업신고증 고유번호", example = "2010 1234567 - 1")
    val licenseId : String,

    @Schema(description = "영업신고증 이미지 Id", example = "IM10000001")
    val licenseImageId : String,

    @Schema(description = "대표자 이름", example = "홍길동")
    val ownerName : String,

    @Schema(description = "상호명", example = "상호명")
    val marketName : String,

    @Schema(description = "소재지", example = "서울특별시 종로구 창성동")
    val address: String,

    @Schema(description = "우편번호", example = "06253")
    val postalCode: String,
)
