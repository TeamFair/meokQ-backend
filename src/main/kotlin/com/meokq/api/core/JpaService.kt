package com.meokq.api.core

import com.meokq.api.core.DataValidation.checkNotNullData
import com.meokq.api.core.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface JpaService<MODEL, ID> {
    var jpaRepository : JpaRepository<MODEL, ID>

    fun saveModel(model: MODEL) : MODEL {
        return jpaRepository.save(model as (MODEL & Any))
    }

    fun saveModels(models: List<MODEL>) : List<MODEL> {
        return jpaRepository.saveAll(models)
    }

    fun findModelById(id : ID) : MODEL {
        checkNotNullData(id, "data is not found by id : $id")
        return jpaRepository.findById(id!!).orElseThrow { NotFoundException("data is not found by id : $id") }
    }

    fun deleteById(id : ID) {
        checkNotNullData(id, "deleteById : id is null")
        return jpaRepository.deleteById(id!!)
    }

}