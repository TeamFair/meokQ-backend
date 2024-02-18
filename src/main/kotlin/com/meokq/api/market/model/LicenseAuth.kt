package com.meokq.api.market.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.file.model.Image
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.UuidGenerator

// 영업신고증 정보
@Entity(name = "tb_license_business")
class LicenseAuth(
    @Id
    @UuidGenerator
    var recordId : String? = null,
    var licenseId : String? = null,
    var ownerName : String? = null, //영업신고증상 대표자명
    var marketName : String? = null, //상호명
    var address : String? = null,
    var postalCode : String? = null,

    @OneToOne
    @JoinColumn(name = "license_image_id")
    var licenseImage : Image? = null,
) : BaseModel()