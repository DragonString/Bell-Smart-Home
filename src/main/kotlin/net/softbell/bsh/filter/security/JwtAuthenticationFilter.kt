package net.softbell.bsh.filter.security

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

/**
 * @author : Bell(bell@softbell.net)
 * @description : 인증 처리 필터
 */
class JwtAuthenticationFilter(jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    private val jwtTokenProvider: JwtTokenProvider = jwtTokenProvider

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        // Init
        val token = jwtTokenProvider.resolveToken(request as HttpServletRequest)

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