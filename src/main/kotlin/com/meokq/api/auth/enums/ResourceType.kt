package com.meokq.api.auth.enums

enum class ResourceType(
    val startWith: List<String> = listOf(),
    val endWith : List<String> = listOf(),
    val needToken : Boolean,
    val needAuthReq : Boolean,
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
        needToken = false, // 토큰 불필요 리소스
        needAuthReq = false,
    ),

    /**
     * 토큰 없이 호출 가능
     */
    OpenResource(
        startWith = listOf("/api/open/"),
        needToken = false, // 토큰 불필요 리소스
        needAuthReq = true,
    ),

    ApiResources(
        startWith = listOf("/api/"),
        needToken = true, // 토큰 필요 리소스
        needAuthReq = true,
    ),

    UNKNOWN(
        needToken = true, // 토큰 필요 리소스
        needAuthReq = true,
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