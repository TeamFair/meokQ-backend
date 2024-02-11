package data

import com.meokq.api.image.enums.ImageType
import com.meokq.api.image.model.Image
import jdk.jfr.Description

object MockImage {
    @Description("영업신고증 이미지")
    val IM10000001 = Image(
        fileId = "IM10000001",
        type = ImageType.BUSINESS_REGISTRATION_CERTIFICATE,
        size = 1024
    )

    @Description("신분증 이미지")
    val IM10000002 = Image(
        fileId = "IM10000002",
        type = ImageType.ID_CARD,
        size = 2048
    )

    @Description("마켓로고 이미지")
    val IM10000003 = Image(
        fileId = "IM10000003",
        type = ImageType.MARKET_LOGO,
        size = 3072
    )

    @Description("영수증 이미지")
    val IM10000004 = Image(
        fileId = "IM10000004",
        type = ImageType.RECEIPT,
        size = 3072
    )
}
