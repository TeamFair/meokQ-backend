package com.meokq.api.application.service

import com.meokq.api.core.converter.BaseConverter
import org.springframework.data.jpa.repository.JpaRepository

interface BaseService<REQ, RES, MODEL, ID> {

    var _converter : BaseConverter<REQ, RES, MODEL>
    var _repository : JpaRepository<MODEL, ID>

    fun save(request : REQ) : RES {
        val model = _converter.requestToModel(request)
        val result = _repository.save(model as (MODEL))
        return _converter.modelToResponse(result)
    }
}