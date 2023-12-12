package com.meokq.api.quest.request

import com.meokq.api.quest.enums.QuestStatus

data class QuestSearchDto(
    val marketId : String? = null,
    val questId : String? = null,
    val questStatus: QuestStatus? = null
)