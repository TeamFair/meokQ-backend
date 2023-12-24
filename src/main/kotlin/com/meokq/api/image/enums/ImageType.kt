package com.meokq.api.image.enums

import com.meokq.api.auth.enums.UserType

enum class ImageType(
    val description: String,
    val selectPermissions: List<UserType> = listOf(), //조회 권한
    val deletePermissions: List<UserType> = listOf(), //삭제 권한
    val createPermissions: List<UserType> = listOf(), //등록 권한
) {
    BUSINESS_REGISTRATION_CERTIFICATE(
        description = "영업신고증",
        createPermissions = listOf(UserType.BOSS),
        selectPermissions = listOf(UserType.BOSS, UserType.ADMIN),
        deletePermissions = listOf(UserType.BOSS, UserType.ADMIN)
    ),
    ID_CARD(
        description = "신분증",
        createPermissions = listOf(UserType.BOSS),
        selectPermissions = listOf(UserType.BOSS, UserType.ADMIN),
        deletePermissions = listOf(UserType.BOSS, UserType.ADMIN)
    ),
    MARKET_LOGO(
        description = "마켓-로고",
        createPermissions = listOf(UserType.BOSS),
        selectPermissions = listOf(UserType.BOSS, UserType.ADMIN, UserType.CUSTOMER, UserType.UNKNOWN),
        deletePermissions = listOf(UserType.BOSS, UserType.ADMIN)
    ),
    RECEIPT(
        description = "영수증",
        createPermissions = listOf(UserType.CUSTOMER),
        selectPermissions = listOf(UserType.BOSS, UserType.CUSTOMER),
        deletePermissions = listOf(UserType.CUSTOMER)
    )
}