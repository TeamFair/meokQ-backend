package com.meokq.api.core.specification

import com.meokq.api.core.util.ReflectionUtils
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

interface BaseSpecificationV2<MODEL> {
    val equalColumns : List<SpecificationDto>

    fun <REQ>bySearchDto(searchDto : REQ) : Specification<MODEL>{
        return Specification { root, query, criteriaBuilder ->

            val predicates = predicatesEqual(
                root = root,
                criteriaBuilder = criteriaBuilder,
                searchDto = searchDto,
            )
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    fun <REQ>predicatesEqual(
        root: Root<MODEL>,
        criteriaBuilder: CriteriaBuilder,
        searchDto: REQ
    ): MutableList<Predicate> {
        val predicates: MutableList<Predicate> = ArrayList()

        equalColumns.forEach {specDto ->
            val value = getValueFrom(
                source = searchDto,
                columnName = specDto.paramName
            )

            if (value!=null && !specDto.isEmpty(value)){
                predicates.add(
                    equal(
                        root.get(specDto.columnName),
                        value,
                        criteriaBuilder
                    )
                )
            }
        }

        return predicates
    }

    fun <REQ>getValueFrom(
        source : REQ,
        columnName : String
    ) : Any? {
        return try {
            ReflectionUtils.getValueWithFieldName(source!!, columnName)
        } catch (e : Exception){
            // reflection error
            null
        }
    }

    fun <T>equal(
        expression: Expression<T>,
        value: T,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.equal(expression, value)
    }

    private fun like(
        expression: Expression<String>,
        pattern: String, criteriaBuilder:
        jakarta.persistence.criteria.CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.like(expression, pattern)
    }

    private fun between(
        expression: Expression<LocalDateTime>,
        start: LocalDateTime,
        end: LocalDateTime,
        criteriaBuilder: jakarta.persistence.criteria.CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.between(expression, start, end)
    }
}