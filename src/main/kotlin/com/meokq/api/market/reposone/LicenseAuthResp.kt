package com.meokq.api.market.reposone

import com.meokq.api.file.response.ImageResp
import com.meokq.api.market.model.LicenseAuth
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Operating-License-Response",
    description = "영업신고증 정보"
)
data class LicenseAuthResp(
    @Schema(description = "영업신고증 고유번호")
    val licenseId : String?,

    @Schema(description = "영업신고증 이미지")
    val licenseImage : ImageResp?,

    @Schema(description = "대표자 이름")
    val ownerName : String?,

    @Schema(description = "상호명")
    val marketName : String?,

    @Schema(description = "소재지")
    val address: String?,

    @Schema(description = "우편번호")
    val postalCode: String?,
){
    constructor(model : LicenseAuth) : this(
        licenseId = model.licenseId,
        licenseImage = model.licenseImage?.let { ImageResp(it) },
        ownerName = model.ownerName,
        marketName = model.marketName,
        address = model.address,
        postalCode = model.postalCode,
    )
}