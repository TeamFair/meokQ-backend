package com.meokq.api.image.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.image.enums.ImageType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_image")
class Image(
    @Id
    /*@GenericGenerator(
        name = "SEQ_GEN_IMAGE",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "IMAGE_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_IMAGE")*/
    @UuidGenerator
    var fileId : String? = null,
    var location: String? = null,
    @Enumerated(EnumType.STRING)
    var type : ImageType? = null,
    var size : Long? = null,
) : BaseModel()