package com.meokq.api.core.converter

import com.meokq.api.application.model.Notice
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.response.NoticeResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NoticeConverter {

    @Autowired
    lateinit var dateTimeConverter: DateTimeConverter

    fun dtoToModel(request: NoticeRequest): Notice {
        return Notice(
            title = request.title,
            content = request.content,
            target = request.target
        )
    }

    fun modelToDto(model: Notice): NoticeResponse {
        return NoticeResponse(
            noticeId = model.noticeId,
            title = model.title,
            content = model.content,
            createDate = dateTimeConverter.convertToString(model.createDate),
            target = model.target
        )
    }
}