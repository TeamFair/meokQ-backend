package com.meokq.api.quest.specification

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.model.Quest
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

object QuestSpecification : BaseSpecificationV2<Quest> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("marketId"),
            SpecificationDto("questId"),
            SpecificationDto("status"),
            SpecificationDto("creatorRole"),
        )

    fun uncompletedQuestList(challengeUserId: String) : Specification<Quest>{
        return Specification { root, query, criteriaBuilder ->
            // 서브쿼리 생성
            val subquery = query.subquery(Challenge::class.java)
            val subRoot = subquery.from(Challenge::class.java)

            // 서브쿼리에 적절한 검색 조건 추가
            val questPredicates = mutableListOf<Predicate>()
            questPredicates.add(
                criteriaBuilder.equal(
                    this.getPath("questId", subRoot),
                    this.getPath("questId", root)
                )
            )

            questPredicates.add(
                criteriaBuilder.equal(
                    this.getPath("customerId", subRoot),
                    challengeUserId
                )
            )

            subquery.select(subRoot).where(*questPredicates.toTypedArray())

            // 메인 쿼리에 검색 조건 추가
            val predicates = ArrayList<Predicate>()
            predicates.add(criteriaBuilder.equal(
                this.getPath("status", root),
                QuestStatus.PUBLISHED
            ))

            predicates.add(criteriaBuilder.exists(subquery).not())
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    fun completedQuestList(challengeUserId: String) : Specification<Quest>{
        return Specification { root, query, criteriaBuilder ->
            // 서브쿼리 생성
            val subquery = query.subquery(Challenge::class.java)
            val subRoot = subquery.from(Challenge::class.java)

            // 서브쿼리에 적절한 검색 조건 추가
            val questPredicates = mutableListOf<Predicate>()
            questPredicates.add(
                criteriaBuilder.equal(
                    this.getPath("questId", subRoot),
                    this.getPath("questId", root)
                )
            )

            questPredicates.add(
                criteriaBuilder.equal(
                    this.getPath("customerId", subRoot),
                    challengeUserId
                )
            )

            questPredicates.add(
                criteriaBuilder.equal(
                    this.getPath("status", subRoot),
                    ChallengeStatus.APPROVED
                )
            )

            subquery.select(subRoot).where(*questPredicates.toTypedArray())

            // 메인 쿼리에 검색 조건 추가
            val predicates = ArrayList<Predicate>()
            predicates.add(criteriaBuilder.equal(
                this.getPath("status", root),
                QuestStatus.PUBLISHED
            ))

            predicates.add(criteriaBuilder.exists(subquery))
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    private fun <T>getPath(paramName : String, root: Root<T>) : Path<String> {
        return root.get(paramName)
    }
}