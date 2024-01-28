package com.meokq.api.user.enums

enum class AgreementType(
    val description: String,
    ) {
    // OLD
    PERSONAL_INFO_COLLECTION("개인정보 수집 이용동의"),
    THIRD_PARTY_PROVIDE("개인정보 3자 제공 동의"),
    PROMOTION_MARKETING("홍보및 마케팅에 관한 동의"),

    // BOSS
    TERMS_OF_SERVICE_BOSS("서비스 이용약관"),
    UNIQUEID_CONSENT_FORM_BOSS("고유식별정보 처리동의서"),
    PRIVACY_POLICY_BOSS("개인정보처리방침"),
    PRIVACY_CONSENT_FORM_BOSS("개인정보처리동의서"),
    PROMOTION_MARKETING_BOSS("홍보 및 마케팅에 관한 동의"),

    // CUSTOMER
    TERMS_OF_SERVICE_USER("서비스 이용약관"),
    PRIVACY_POLICY_USER("개인정보처리방침"),
    PRIVACY_CONSENT_FORM_USER("개인정보처리동의서"),
    PROMOTION_MARKETING_USER("홍보 및 마케팅에 관한 동의"),
    ;

    companion object {
        fun fromDescription(description: String): AgreementType? =
            values().find { it.description == description }
    }
}
