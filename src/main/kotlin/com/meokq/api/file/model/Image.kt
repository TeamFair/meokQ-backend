package com.meokq.api.file.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.request.ImageReq
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity(name = "tb_image")
class Image(
    @Id
    /*@GenericGenerator(
        name = "SEQ_GEN_IMAGE",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "IMAGE_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_IMAGE")*/
    //@UuidGenerator
    var fileId: String? = null,
    @Enumerated(EnumType.STRING)
    var type: ImageType? = null,
    var size: Long? = null,
    var isDelete: Boolean = false
) : BaseModel() {
    constructor(request: ImageReq, fileId: String): this(
        fileId = fileId,
        type = request.type,
        size = request.file.size
    )

}