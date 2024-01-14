package com.meokq.api.core.service

import com.meokq.api.core.repository.BaseRepository
import org.springframework.data.jpa.domain.Specification

interface JpaSpecExecutor<T, ID> {
    val sRepository : BaseRepository<T, ID>
    val specification : Specification<T>

    fun count(searchDto: Any) : Long = sRepository.count(specification)
    fun exists(searchDto: Any) : Boolean = sRepository.exists(specification)
    fun findModels(searchDto : Any) : List<T> = sRepository.findAll(specification)
    fun deleteModels(searchDto: Any) : Long = sRepository.delete(specification)
}