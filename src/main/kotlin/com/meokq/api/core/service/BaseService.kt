package com.meokq.api.core.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.DataException
import com.meokq.api.core.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface BaseService<REQ, RES, MODEL, ID> {

    var _converter : BaseConverter<REQ, RES, MODEL>
    var _repository : JpaRepository<MODEL, ID>

    /**
     * base - curd
     */
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

    fun findById(id : ID) : RES {
        checkNotNull(id)
        return _converter.modelToResponse(
            _repository.findById(id).orElseThrow { NotFoundException("data is not found by id : $id") }
        )
    }

    fun findModelById(id : ID) : MODEL {
        checkNotNull(id)
        return _repository.findById(id).orElseThrow { NotFoundException("data is not found by id : $id") }
    }

    fun deleteById(id : ID) {
        checkNotNull(id)
        return _repository.deleteById(id)
    }

    /**
     * withAuth - curd
     */
    fun deleteByIdWithAuth(id : ID, authReq: AuthReq) {
        //checkAuthCallback.beforeDelete(authReq)
        deleteById(id)
    }

    /**
     * check validation
     */
    fun checkNotNullData(value : Any?, message : String){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw DataException("DataException : $message")
        }
    }

    /**
     * etc
     */

    fun getBasePageableWithSorting(pageable : Pageable): PageRequest {
        return PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )
    }
}