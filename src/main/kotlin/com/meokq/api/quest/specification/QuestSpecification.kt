package com.meokq.api.quest.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.quest.model.Quest

object QuestSpecification : BaseSpecificationV2<Quest> {

    override val equalColumns: List<SpecificationDto>
        get() = listOf(
            SpecificationDto("marketId"),
            SpecificationDto("questId"),
            SpecificationDto("status"),
            SpecificationDto("creatorRole"),
        )

}