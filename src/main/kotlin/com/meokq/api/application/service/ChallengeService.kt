package com.meokq.api.application.service

import com.meokq.api.application.enums.ChallengeStatus
import com.meokq.api.application.model.Challenge
import com.meokq.api.application.repository.ChallengeRepository
import com.meokq.api.application.request.ChallengeRequest
import com.meokq.api.application.response.ChallengeResponse
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.ChallengeConverter
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ChallengeService(
    private val converter : ChallengeConverter,
    private val repository: ChallengeRepository,
    private val questService : QuestService,
) : BaseService<ChallengeRequest, ChallengeResponse, Challenge, String> {
    override var _converter: BaseConverter<ChallengeRequest, ChallengeResponse, Challenge> = converter
    override var _repository: JpaRepository<Challenge, String> = repository

    fun findAllByMarketIdAndStatus(marketId: String, status: ChallengeStatus): List<ChallengeResponse> {
        // 마켓 아이디로 퀘스트 목록을 가져옵니다.
        val quests = questService.findAllByMarketId(marketId, Pageable.unpaged())

        // 각 퀘스트 아이디를 기반으로 도전 내역을 조회하고 리스트에 추가합니다.
        val challenges = mutableListOf<Challenge>()
        for (quest in quests) {
            quest.let {
                val questChallenges = repository.findAllByQuestIdAndStatus(it.questId?:throw Exception("quest id is null"), status, Pageable.unpaged())
                challenges.addAll(questChallenges)
            }
        }

        return converter.modelToResponse(challenges)
    }
}