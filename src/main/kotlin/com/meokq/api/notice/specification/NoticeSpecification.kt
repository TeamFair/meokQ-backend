package com.meokq.api.notice.specification

import com.meokq.api.core.specification.BaseSpecificationV2
import com.meokq.api.core.specification.SpecificationDto
import com.meokq.api.notice.model.Notice

object NoticeSpecification : BaseSpecificationV2<Notice> {
    override val equalColumns: List<SpecificationDto>
        get() = listOf(SpecificationDto("target"))
}