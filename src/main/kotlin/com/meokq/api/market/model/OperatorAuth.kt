package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.image.model.Image
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
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

    @OneToOne
    @JoinColumn(name = "idcard_image_id")
    val idcardImage: Image? = null,
) : BaseModel()