import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.filters.RedisTokenFilter
import com.meokq.api.auth.service.AuthService
import com.meokq.api.core.exception.TokenException
import jakarta.servlet.FilterChain
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

internal class RedisTokenFilterTest {

    private lateinit var redisTokenFilter: RedisTokenFilter
    private lateinit var authService: AuthService
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setUp() {
        SecurityContextHolder.clearContext() // Clear the SecurityContext before each test
        authService = mock(AuthService::class.java)
        redisTokenFilter = RedisTokenFilter(authService)
        filterChain = mock(FilterChain::class.java)
    }

    private fun setAuthentication(userId: String, userType: UserType) {
        val authReq = AuthReq(userId = userId, userType = userType)
        val authentication = UsernamePasswordAuthenticationToken(
            authReq,
            null,
            listOf(SimpleGrantedAuthority(authReq.userType.authorization))
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    @Test
    @DisplayName("유효한 토큰이 요청에 포함되었을 때 필터 체인이 정상적으로 동작한다")
    fun `valid token allows request to proceed`() {
        // Given
        val userId = "user1"
        val token = "validToken"
        val request = MockHttpServletRequest()
        request.addHeader("authorization", token)
        val response = MockHttpServletResponse()

        setAuthentication(userId, UserType.CUSTOMER)

        `when`(authService.isTokenValid(userId, token)).thenReturn(true)

        // When
        redisTokenFilter.doFilterInternal(request, response, filterChain)

        // Then
        verify(authService).isTokenValid(userId, token)
        verify(filterChain).doFilter(request, response)
    }

    @Test
    @DisplayName("유효하지 않은 토큰이 요청에 포함되었을 때 401 상태 코드를 반환한다")
    fun `invalid token returns unauthorized`() {
        // Given
        val userId = "user1"
        val token = "invalidToken"
        val request = MockHttpServletRequest()
        request.addHeader("authorization", token)
        val response = MockHttpServletResponse()

        setAuthentication(userId, UserType.CUSTOMER)

        `when`(authService.isTokenValid(userId, token)).thenReturn(false)

        // When
        redisTokenFilter.doFilterInternal(request, response, filterChain)

        // Then
        verify(authService).isTokenValid(userId, token)
        verifyNoInteractions(filterChain)
        assertEquals(401, response.status)
    }
}