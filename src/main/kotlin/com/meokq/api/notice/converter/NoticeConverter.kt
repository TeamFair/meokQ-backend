package com.meokq.api.notice.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.notice.model.Notice
import com.meokq.api.notice.request.NoticeReq
import com.meokq.api.notice.response.NoticeResp
import org.springframework.stereotype.Component

@Component
@Deprecated("converter to data-model")
class NoticeConverter : BaseConverter<NoticeReq, NoticeResp, Notice> {

    override fun modelToResponse(model: Notice): NoticeResp {
        return NoticeResp(model)
    }

    override fun requestToModel(request: NoticeReq): Notice {
        return Notice(request)
    }
}