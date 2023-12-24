package com.meokq.api.quest.specification

import com.meokq.api.core.specification.BaseSpecification
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.user.specification.AgreementSpecification
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object QuestSpecification : BaseSpecification<QuestSearchDto, Quest>() {
    override fun bySearchDto(searchDto: QuestSearchDto): Specification<Quest> {
        return Specification { root, query, criteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (searchDto.marketId != null) {
                predicates.add(
                    AgreementSpecification.equal(
                        root.get("marketId"),
                        searchDto.marketId,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.status != null){
                predicates.add(
                    AgreementSpecification.equal(
                        root.get("questStatus"),
                        searchDto.status,
                        criteriaBuilder
                    )
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

}