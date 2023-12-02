package com.meokq.api.user.specification

import com.meokq.api.core.specification.BaseSpecification
import com.meokq.api.user.model.Agreement
import com.meokq.api.user.request.AgreementSearchDto
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object AgreementSpecification : BaseSpecification<AgreementSearchDto, Agreement>() {
    override fun bySearchDto(searchDto: AgreementSearchDto) : Specification<Agreement> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.userId != null) {
                predicates.add(
                    equal(
                        root.get("userId"),
                        searchDto.userId,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.agreementType != null){
                predicates.add(
                    equal(
                        root.get("agreementType"),
                        searchDto.agreementType,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.creatDateFrom != null && searchDto.creatDateTo != null) {
                predicates.add(
                    between(
                        root.get("createDate"),
                        searchDto.creatDateFrom,
                        searchDto.creatDateTo,
                        criteriaBuilder
                    )
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}