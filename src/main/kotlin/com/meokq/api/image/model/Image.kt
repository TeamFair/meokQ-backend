package com.meokq.api.image.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.image.enums.ImageType
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