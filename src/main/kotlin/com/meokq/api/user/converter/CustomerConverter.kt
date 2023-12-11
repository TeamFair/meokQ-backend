package com.meokq.api.user.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.user.model.Customer
import com.meokq.api.user.request.CustomerReq
import com.meokq.api.user.response.CustomerResp
import org.springframework.stereotype.Component

@Component
class CustomerConverter : BaseConverter<CustomerReq, CustomerResp, Customer> {
    override fun modelToResponse(model: Customer): CustomerResp {
        return CustomerResp(
            status = model.status,
            customerId = model.customerId,
        )
    }

    override fun requestToModel(request: CustomerReq): Customer {
        return Customer(
            email = request.email,
        )
    }
}