package com.meokq.api.rank

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.service.QuestService
import org.springframework.stereotype.Component
/*
//TODO 퀘스트 이모지 랭크 서비스 구현
@Component
class QuestEmojiRankService(
): EmojiRankService<Quest> {
    override var upperRank: MutableList<Quest> = mutableListOf()
    override var lowerRank: MutableList<Quest> = mutableListOf()


    override fun addToRank(item: Quest) {
        if (validLowerRank(item)){
            lowerRank.add(item)
        }else{
            addToUpperRank(item)
        }
    }

    private fun validLowerRank(item: Quest): Boolean {
         throw NotImplementedError("구현되지 않은 기능 입니다. 관리자에게 문의 주세요.")
    }

    private fun addToUpperRank(item: Quest) {
        if (lowerRank.remove(item)) {
            upperRank.add(item)
        }
    }

}*/
