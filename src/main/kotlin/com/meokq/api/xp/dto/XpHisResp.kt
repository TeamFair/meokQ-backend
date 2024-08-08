package com.meokq.api.xp.dto

import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.xp.model.XpHistory

data class XpHisResp(
    val recordId: Long?,
    val title: String?,
    val xpPoint: Long,
    var createDate : String?
){
    constructor(model: XpHistory): this(
        recordId = model.recordId,
        title = model.title,
        xpPoint = model.xpPoint,
        createDate = model.createDate?.let { DateTimeConverterV2.convertToString(it) }
    )
}
