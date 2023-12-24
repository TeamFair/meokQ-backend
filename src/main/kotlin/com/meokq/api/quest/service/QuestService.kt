package com.meokq.api.quest.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.quest.converter.QuestConverter
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.repository.QuestRepository
import com.meokq.api.quest.request.QuestReq
import com.meokq.api.quest.request.QuestSearchDto
import com.meokq.api.quest.response.QuestResp
import com.meokq.api.quest.specification.QuestSpecification
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class QuestService(
    private val repository : QuestRepository,
    private val converter : QuestConverter,
    private val missionService: MissionService,
    private val rewardService: RewardService,
    //private val marketService: MarketService,
) : BaseService<QuestReq, QuestResp, Quest, String> {
    override var _converter: BaseConverter<QuestReq, QuestResp, Quest> = converter
    override var _repository: JpaRepository<Quest, String> = repository

    fun findAll(searchDto: QuestSearchDto, pageable: Pageable): PageImpl<QuestResp> {
        val pageableWithSorting = getBasePageableWithSorting(pageable)
        val specification = QuestSpecification.bySearchDto(searchDto)
        val page = repository.findAll(specification, pageableWithSorting)
        val content = page.content.map{ converter.modelToResponse(it) }

        // TODO : quest-list를 조회할 때에도 미션과 보상정보를 모두 보여주는 것은 비효율적이라고 생각.
        content.forEach {quest ->
            quest.questId.let {
                // TODO : 유효하지 않은 미션과 보상을 어떻게 처리할지 확인 필요.
                try {
                    checkNotNullData(it, "퀘스트 아이디가 등록되지 않았습니다.")
                    quest.missions = missionService.findAllByQuestId(it!!)
                    quest.rewards = rewardService.findAllByQuestId(it)
                } catch (e : Exception){
                    //throw e
                }
            }
        }

        return PageImpl(content, pageable, page.numberOfElements.toLong())
    }

    override fun findById(questId : String) : QuestResp {
        val quest = repository.findById(questId).orElseThrow { throw NotFoundException("quest is not found!!") }
        val questResp = converter.modelToResponse(quest)
        missionService.findAllByQuestId(questId).also { questResp.missions = it }
        rewardService.findAllByQuestId(questId).also { questResp.rewards = it }

        return questResp
    }

    override fun save(request : QuestReq) : QuestResp {
        // save quest
        val notice = converter.requestToModel(request)
        val model = repository.save(notice)
        val response = converter.modelToCreateResp(model)

        response.questId.also {
            checkNotNullData(it, "해당 퀘스트에는 마켓정보가 등록되어 있지 않습니다.")

            // save mission
            missionService.saveAll(it!!, request.missions)

            // save reward
            rewardService.saveAll(it, request.rewards)
        }

        //TODO : 티켓차감
        return response
    }

    override fun deleteById(questId: String, authReq: AuthReq) {
        // check permit
        val quest = repository.findById(questId).orElseThrow {NotFoundException("존재하지 않는 quest 입니다.")}
        //checkPermitForDelete(quest, authReq)

        // delete quest
        super.deleteById(questId)

        // delete mission
        missionService.deleteAllByQuestId(questId)

        // delete reward
        rewardService.deleteAllByQuestId(questId)
    }

    /**
     * Boss는 검토중인 quest만 삭제할 수 있습니다.
     * Admin은 검토중인 quest만 삭제할 수 있습니다.
     * Customer은 quest를 삭제할 수 없습니다.
     */
    /*private fun checkPermitForDelete(quest: Quest, authReq: AuthReq){
        if (quest.questStatus.couldDelete)
            throw InvalidRequestException("""
                    quest 상태를 확인해주세요.(해당 퀘스트상태 = ${quest.questStatus})
                """.trimIndent())

        checkNotNullData(quest.questId, "해당 퀘스트에는 마켓정보가 등록되어 있지 않습니다.")

        // TODO  : 확인
        val markets = marketService.findAll(
            MarketSearchDto(
                presidentId = authReq.userId,
                marketId = quest.marketId
            ),
            authReq = AuthReq()
        ).content

        if (markets.isEmpty()) throw InvalidRequestException("""
                소유한 마켓에서 등록한 퀘스트만 삭제할 수 있습니다.
            """.trimIndent())
    }*/

    fun count(searchDto: QuestSearchDto) : Long {
        val specification = QuestSpecification.bySearchDto(searchDto)
        return repository.count(specification)
    }
}