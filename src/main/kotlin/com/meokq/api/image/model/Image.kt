package com.meokq.api.image.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.image.enums.ImageType
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "tb_image")
class Image(
    @Id
    //@UuidGenerator
    var fileId : String? = null,
    var location: String? = null,
    var type : ImageType? = null,
    var size : Long? = null,
) : BaseModel()