package com.meokq.api.challenge.service

import com.meokq.api.challenge.converter.ChallengeConverter
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.repository.ChallengeRepository
import com.meokq.api.challenge.request.ChallengeReq
import com.meokq.api.challenge.response.ChallengeResp
import com.meokq.api.challenge.specification.ChallengeSpecifications
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.market.request.ChallengeSearchDto
import com.meokq.api.quest.service.QuestService
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val converter : ChallengeConverter,
    private val repository: ChallengeRepository,
    private val questService: QuestService,
) : BaseService<ChallengeReq, ChallengeResp, Challenge, String> {
    override var _converter: BaseConverter<ChallengeReq, ChallengeResp, Challenge> = converter
    override var _repository: JpaRepository<Challenge, String> = repository

    fun findAll(searchDto : ChallengeSearchDto, pageable: Pageable): Page<ChallengeResp> {
        val pageableWithSorting = PageRequest.of(
            pageable.pageNumber, pageable.pageSize, Sort.by("createDate").descending()
        )

        val specification = ChallengeSpecifications.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)

        val content = page.content.map{ converter.modelToResponse(it) }
        // TODO : 제목을 어떻게 보여줄지 확인필요.
        /*page.content.forEachIndexed { idx, resp ->
            // select quest
            resp.questId?.let { content[idx].quest = questService.findById(it) }
        }*/

        return PageImpl(content, pageable, page.numberOfElements.toLong())
    }

    override fun findById(id: String): ChallengeResp {
        val challenge = repository.findById(id).orElseThrow{NotFoundException("challenge not found with ID: $id")}
        val challengeResp = converter.modelToResponse(challenge).apply {
            quest = challenge.challengeId?.let { questService.findById(it) }
        }

        return challengeResp
    }

}