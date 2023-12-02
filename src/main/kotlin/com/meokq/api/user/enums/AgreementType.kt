package com.meokq.api.user.enums

enum class AgreementType(val description: String) {
    PERSONAL_INFO_COLLECTION("개인정보 수집 이용동의"),
    THIRD_PARTY_PROVIDE("개인정보 3자 제공 동의"),
    PROMOTION_MARKETING("홍보및 마케팅에 관한 동의");

    // 추가적으로 필요한 속성이나 메서드가 있다면 여기에 추가할 수 있습니다.

    companion object {
        fun fromDescription(description: String): AgreementType? =
            values().find { it.description == description }
    }
}
