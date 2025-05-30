package com.meokq.api.title.response

import com.meokq.api.title.enums.TitleType
import com.meokq.api.title.model.Title
import java.time.LocalDateTime

data class TitleResp (
    val id: String?,
    val name: String?,
    val condition: String?,
    val type: TitleType?,
    val createdAt : LocalDateTime?,
) {
    constructor(title: Title) : this(
        id = title.id,
        name = title.name,
        condition = title.condition,
        type = title.type,
        createdAt = title.createDate,
    )
}

data class TitleAdminResp (
    val id: String?,
    val name: String?,
    var condition: String?,
    var type: TitleType?,
    var useYn : Boolean?,
) {
    constructor(title: Title) : this(
        id = title.id,
        name = title.name,
        type = title.type,
        useYn = title.useYn,
        condition = title.condition,
    )
}
