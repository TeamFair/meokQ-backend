package com.meokq.api.core.service

import com.meokq.api.application.converter.BaseConverter
import org.springframework.data.jpa.repository.JpaRepository

interface BaseService<REQ, RES, MODEL, ID> {

    var _converter : BaseConverter<REQ, RES, MODEL>
    var _repository : JpaRepository<MODEL, ID>

    fun save(request : REQ) : RES {
        val model = _converter.requestToModel(request)
        val result = _repository.save(model as (MODEL & Any))
        return _converter.modelToResponse(result)
    }

    fun saveAll(requestList : List<REQ>) : List<RES> {
        return _converter.modelToResponse(
            _repository.saveAll(
                _converter.requestToModel(requestList)
            )
        )
    }
}