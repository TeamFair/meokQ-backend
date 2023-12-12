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

            if (searchDto.questId != null){
                predicates.add(
                    AgreementSpecification.equal(
                        root.get("questId"),
                        searchDto.questId,
                        criteriaBuilder
                    )
                )
            }

            if (searchDto.questStatus != null){
                predicates.add(
                    AgreementSpecification.equal(
                        root.get("questStatus"),
                        searchDto.questStatus,
                        criteriaBuilder
                    )
                )
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

}