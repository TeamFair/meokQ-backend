package com.meokq.api.title.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.title.enums.TitleType
import jakarta.persistence.*

@Entity
@Table(name = "tb_title")
class Title (
    var name: String,
    var condition: String,
    @Enumerated(EnumType.STRING)
    var type: TitleType,
    var useYn : Boolean = true,
    @Id
    var id: String? = null,
): BaseModel() {
}


