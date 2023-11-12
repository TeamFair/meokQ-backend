package com.meokq.api.application.converter

import com.meokq.api.application.model.Notice
import com.meokq.api.application.request.NoticeReq
import com.meokq.api.application.response.NoticeResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NoticeConverter : BaseConverter<NoticeReq, NoticeResp, Notice> {

    @Autowired
    lateinit var dateTimeConverter: DateTimeConverter

    override fun modelToResponse(model: Notice): NoticeResp {
        return NoticeResp(
            noticeId = model.noticeId,
            title = model.title,
            content = model.content,
            createDate = dateTimeConverter.convertToString(model.createDate),
            target = model.target
        )
    }

    override fun requestToModel(request: NoticeReq): Notice {
        return Notice(
            title = request.title,
            content = request.content,
            target = request.target
        )
    }
}