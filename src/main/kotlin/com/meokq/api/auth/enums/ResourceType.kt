package com.meokq.api.auth.enums

enum class ResourceType(
    val startWith: List<String> = listOf(),
    val endWith : List<String> = listOf(),
    val isAuthTarget : Boolean,
) {
    StaticResources(
        startWith = listOf(
            "/css/", "/js/", "/images/",
            "/swagger-ui/", "/v3/api-docs/"
        ),
        endWith = listOf(
            ".ico",
            "/swagger-config"
        ),
        isAuthTarget = false, // 인가 불필요 리소스
    ),

    ApiResources(
        startWith = listOf("/api/"),
        isAuthTarget = true, // 인가 필요 리소스
    ),

    /**
     * 토큰 없이 호출 가능
     */
    OpenResource(
        startWith = listOf("/auth/login"),
        isAuthTarget = false, // 인가 불필요 리소스
    ),

    UNKNOWN(
        isAuthTarget = true, // 인가 리소스
    );

    companion object {
        fun getResourceType(requestUri : String) : ResourceType {
            val rs = values().find {type ->
                // start
                val start = type.startWith.any { s ->
                    requestUri.startsWith(s)
                }

                // end
                val end = type.endWith.any{e ->
                    requestUri.endsWith(e)
                }

                start||end
            }

            return requireNotNull(rs) {
                "No enum constant ResourceType for string: $requestUri"
            }
        }
    }
}