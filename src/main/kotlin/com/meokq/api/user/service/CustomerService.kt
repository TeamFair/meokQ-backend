package com.meokq.api.user.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.core.service.BaseService
import com.meokq.api.user.converter.CustomerConverter
import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.user.request.CustomerReq
import com.meokq.api.user.response.CustomerResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val converter : CustomerConverter,
    private val repository : CustomerRepository
) : BaseService<CustomerReq, CustomerResp, Customer, String>{
    override var _converter: BaseConverter<CustomerReq, CustomerResp, Customer> = converter
    override var _repository: JpaRepository<Customer, String> = repository

    fun findByEmail(email : String): CustomerResp {
        return converter.modelToResponse(
            repository.findByEmail(email) ?: throw NotFoundException("customer is not found by email : $email")
        )
    }

    override fun save(request: CustomerReq): CustomerResp {
        if (repository.findByEmail(request.email) != null) {
            throw NotUniqueException()
        }
        return super.save(request)
    }
}