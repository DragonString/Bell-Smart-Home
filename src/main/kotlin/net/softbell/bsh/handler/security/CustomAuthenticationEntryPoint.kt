package net.softbell.bsh.handler.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author : Bell(bell@softbell.net)
 * @description : 인증 실패 핸들러
 */
@Component
class CustomAuthenticationEntryPoint(
        private val G_API_URI: String = "",
        private val G_VIEW_URI: String = ""
) : AuthenticationEntryPoint {
    // 인증이 안 된 요청일 때 진입
    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, ex: AuthenticationException) {
        // Field
        var isAPI = false

        // Init
        if (request.requestURI.startsWith("/api/"))
            isAPI = true

        // Redirect
        if (isAPI)
            response.sendRedirect(G_API_URI)
        else
            response.sendRedirect(G_VIEW_URI)
    }
}