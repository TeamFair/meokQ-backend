package com.meokq.api.application.model

import com.meokq.api.application.enums.ImageType
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_image")
class Image(
    @Id
    @UuidGenerator
    var imageId : String? = null,
    var location: String? = null,
    var type : ImageType? = null,
    var originalFilename : String? = null,
    var size : Long? = null
) : BaseModel()