package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.market.request.OperatorAuthReq
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate

// 사업자 개인정보
@Entity(name = "tb_operator_info")
class OperatorAuth (
    @Id
    @UuidGenerator
    var recordId : String? = null,
    val ownerName: String? = null,
    val ownerBirthdate: LocalDate? = null,
    val idcardImageId: String? = null,

    /*@OneToOne
    @JoinColumn(name = "idcard_image_id")
    val idcardImage: Image? = null,*/
) : BaseModel() {
    constructor(request: OperatorAuthReq) : this(
        ownerName = request.name,
        ownerBirthdate = request.birthdate,
        idcardImageId = request.idcardImageId,
    )
}