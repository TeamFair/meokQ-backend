package com.meokq.api.application.model.entity

import com.meokq.api.application.enums.ImageType
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_Image")
class Image : BaseModel() {
    @Id
    var imageId : String? = null

    var imageUrl : String? = null
    var type : ImageType? = null
    var noticeId : String? = null
}