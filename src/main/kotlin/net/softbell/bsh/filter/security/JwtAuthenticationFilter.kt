package net.softbell.bsh.filter.security

import lombok.AllArgsConstructor
import net.softbell.bsh.component.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 처리 필터
 */
@AllArgsConstructor
class JwtAuthenticationFilter : GenericFilterBean() {
    private val jwtTokenProvider: JwtTokenProvider? = null
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        // Field
        val token: String?

        // Init
        token = jwtTokenProvider!!.resolveToken(request as HttpServletRequest)

        // Process
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val auth = jwtTokenProvider.getAuthentication(token)
            if (!jwtTokenProvider.checkMaintenanceLogin(auth)) {
                filterChain.doFilter(request, response)
                return
            }
            SecurityContextHolder.getContext().authentication = auth
            if (!jwtTokenProvider.isApiMode(request)) // API 모드가 아니면
                jwtTokenProvider.setCookieAuth(request, response as HttpServletResponse, auth) // 인증 토큰 만료 기간 연장
        }

        // Filter
        filterChain.doFilter(request, response)
    }
}