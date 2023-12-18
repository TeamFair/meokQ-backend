package com.meokq.api.user.model

import com.meokq.api.core.enums.TypeYN
import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.AgreementType
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "tb_agreement_history")
data class Agreement(
    @Id
    @UuidGenerator
    var agreementId: String? = null,
    var userId: String? = null, // BOSS, CUSTOMER model 의 id 와 연결되는 외부 키
    @Enumerated(EnumType.STRING)
    var agreementType: AgreementType? = null,
    var version : Int? = null,
    @Enumerated(EnumType.STRING)
    var acceptYn: TypeYN? = null,
) : BaseModel()