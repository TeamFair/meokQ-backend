package com.meokq.api.agreement.enums

import com.meokq.api.auth.enums.UserType
import com.meokq.api.core.exception.InvalidRequestException

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
    SMS_CONSENT_FORM_BOSS("SMS 수신 동의"),
    EMAIL_CONSENT_FORM_BOSS("이메일 수신 동의"),
    MARKETING_PRIVACY_CONSENT_FORM_BOSS("개인정보 수집 이용 동의"),

    // CUSTOMER
    TERMS_OF_SERVICE_CUSTOMER("서비스 이용약관"),
    PRIVACY_POLICY_CUSTOMER("개인정보처리방침"),
    PRIVACY_CONSENT_FORM_CUSTOMER("개인정보처리동의서"),
    PROMOTION_MARKETING_CUSTOMER("홍보 및 마케팅에 관한 동의"),
    SMS_CONSENT_FORM_CUSTOMER("SMS 수신 동의"),
    EMAIL_CONSENT_FORM_CUSTOMER("이메일 수신 동의"),
    MARKETING_PRIVACY_CONSENT_FORM_CUSTOMER("개인정보 수집 이용 동의"),
    ;

    fun checkTarget(userType: UserType){
        if (this.name.endsWith(userType.name)) return
        throw InvalidRequestException("${userType.name} 유형의 사용자는 ${this.name}약관을 사용할수 없습니다.")
    }

    companion object {
        fun fromDescription(description: String): AgreementType? =
            values().find { it.description == description }
    }
}
