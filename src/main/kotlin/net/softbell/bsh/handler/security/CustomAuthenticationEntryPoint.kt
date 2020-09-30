package net.softbell.bsh.handler.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 실패 핸들러
 */
@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    // Global Field
    private val G_API_URI: String
    private val G_VIEW_URI: String

    constructor() {
        this.G_API_URI = ""
        this.G_VIEW_URI = ""
    }

    constructor(G_API_URI: String, G_VIEW_URI: String) {
        this.G_API_URI = G_API_URI
        this.G_VIEW_URI = G_VIEW_URI
    }

    // 인증이 안 된 요청일 때 진입
    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, ex: AuthenticationException) {
        // Field
        var isAPI = false

        // Init
        if (request.requestURI.startsWith("/api/")) isAPI = true

        // Redirect
        if (isAPI) response.sendRedirect(G_API_URI) else response.sendRedirect(G_VIEW_URI)
    }
}