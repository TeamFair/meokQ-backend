package com.meokq.api.quest.response

import com.meokq.api.quest.model.Quest

class QuestCreateResp(
    val questId: String?
){
    constructor(model : Quest) : this(
        questId = model.questId
    )
}