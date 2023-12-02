package com.meokq.api.user.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.DateTimeConverter
import com.meokq.api.user.model.Agreement
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.response.AgreementResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AgreementConverter : BaseConverter<AgreementReq, AgreementResp, Agreement> {

    @Autowired
    lateinit var dateTimeConverter: DateTimeConverter

    override fun modelToResponse(model: Agreement): AgreementResp {
        return AgreementResp(
            agreementType = model.agreementType,
            version = model.version,
            acceptYn = model.acceptYn,
            acceptDate = dateTimeConverter.convertToString(model.createDate)
        )
    }

    override fun requestToModel(request: AgreementReq): Agreement {
        return Agreement(
            userId = request.userId,
            agreementType = request.agreementType,
            version = request.version,
            acceptYn = request.acceptYn,
        )
    }

}