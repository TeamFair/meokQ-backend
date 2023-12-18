package com.meokq.api.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    //fun findAll(spec: Specification<T>, pageable: Pageable): Page<T>
    //fun deleteAll(spec: Specification<T>?) : Int
}