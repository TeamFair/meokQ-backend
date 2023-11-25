package com.meokq.api.application.converter

import com.meokq.api.application.model.MarketAuth
import com.meokq.api.application.request.MarketAuthReq
import com.meokq.api.application.response.LicenseAuthResp
import com.meokq.api.application.response.MarketAuthResp
import com.meokq.api.application.response.OwnerAuthResp
import org.springframework.stereotype.Component

@Component
class MarketAuthConverter : BaseConverter<MarketAuthReq, MarketAuthResp, MarketAuth> {
    override fun modelToResponse(model: MarketAuth): MarketAuthResp {
        return MarketAuthResp(
            recordId = model.recordId,
            marketId = model.marketId,
            reviewResult = model.reviewResult,
            comment = model.comment,
            owner = null,
            license = null,
        )
    }

    fun modelToDetailResponse(model : MarketAuth) : MarketAuthResp{
        // 영업신고증 정보
        val license = LicenseAuthResp(
            licenseId = model.licenseId,
            licenseImage = null,
            ownerName = model.ownerName,
            marketName = model.marketName,
            address = model.address,
            postalCode = model.postalCode,
        )

        // 사업자 개인정보
        val owner = OwnerAuthResp(
            name = model.ownerName,
            birthdate = model.ownerBirthdate,
            idcardImage = null
        )

        return MarketAuthResp(
            recordId = model.recordId,
            marketId = model.marketId,
            reviewResult = model.reviewResult,
            comment = model.comment,
            owner = owner,
            license = license,
        )
    }

    override fun requestToModel(request: MarketAuthReq): MarketAuth {
        return MarketAuth(
            marketId = request.marketId,

            // 영업신고증 정보
            licenseId = request.license.licenseId,
            licenseImageId = request.license.licenseImageId,
            ownerNameOnLicense = request.license.ownerName,
            marketName = request.license.marketName,
            address = request.license.address,
            postalCode = request.license.postalCode,

            // 사업자 개인정보
            ownerName = request.owner.name,
            ownerBirthdate = request.owner.birthdate,
            idCardImageId = request.owner.idcardImageId,
        )
    }
}