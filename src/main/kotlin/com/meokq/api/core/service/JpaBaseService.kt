package com.meokq.api.core.service

import com.meokq.api.core.exception.DataException
import com.meokq.api.core.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface JpaBaseService<T, ID> {
    val jpaRepository : JpaRepository<T, ID>

    /**
     * curd
     */
    fun saveModel(model: T & Any) : T = jpaRepository.save(model)
    fun saveModels(models : List<T & Any>): List<T> = jpaRepository.saveAll(models)
    fun findModelById(id : ID & Any) : T = jpaRepository.findById(id).orElseThrow { NotFoundException("data is not found by id : $id") }
    fun deleteModelById(id : ID & Any) : Unit = jpaRepository.deleteById(id)

    /**
     * check validation
     */
    fun checkNotNullData(value : Any?, message : String){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw DataException("DataException : $message")
        }
    }
}