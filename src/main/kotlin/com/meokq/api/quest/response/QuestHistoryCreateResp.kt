package com.meokq.api.quest.response

import com.meokq.api.quest.model.QuestHistory

class QuestHistoryCreateResp (questHistory: QuestHistory){
    val questHistoryId : String = questHistory.questHistoryId!!
    val questId :String = questHistory.questId!!
}