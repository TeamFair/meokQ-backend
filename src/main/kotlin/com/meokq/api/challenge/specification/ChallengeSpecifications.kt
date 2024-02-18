package com.meokq.api.challenge.specification

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.request.ChallengeSearchDto
import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.quest.model.Quest
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

object ChallengeSpecifications : BaseSpecificationV2<Challenge> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("status"),
            SpecificationDto("questId"),
            SpecificationDto(columnName = "customerId", paramName = "userId")
        )


    fun joinAndFetch(searchDto: ChallengeSearchDto): Specification<Challenge> {
        return Specification { root, query, criteriaBuilder ->
            // 서브쿼리 생성
            val subquery = query.subquery(Quest::class.java)
            val subRoot = subquery.from(Quest::class.java)

            // 서브쿼리에 적절한 검색 조건 추가
            val questPredicates = mutableListOf<Predicate>()
            questPredicates.add(
                criteriaBuilder.equal(
                    getPath("questId", subRoot),
                    getPath("questId", root)
                )
            )

            if (!searchDto.marketId.isNullOrEmpty()){
                questPredicates.add(
                    criteriaBuilder.equal(
                        getPath("marketId", subRoot),
                        searchDto.marketId
                    )
                )
            }

            subquery.select(subRoot).where(*questPredicates.toTypedArray())

            // 메인 쿼리에 검색 조건 추가
            val predicates = predicatesEqual(
                root = root,
                criteriaBuilder = criteriaBuilder,
                searchDto = searchDto,
            )

            predicates.add(criteriaBuilder.exists(subquery))
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    private fun <T>getPath(paramName : String, root: Root<T>) : Path<String> {
        return root.get(paramName)
    }

}