package net.softbell.bsh.handler.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 접근 제한 핸들러
 */
@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {
    // Global Field
    private val G_API_URI: String? = null
    private val G_VIEW_URI: String? = null

    @Throws(IOException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exception: AccessDeniedException) {
        // Field
        var isAPI = false

        // Init
        if (request.requestURI.startsWith("/api/")) isAPI = true

        // Redirect
        if (isAPI) response.sendRedirect(G_API_URI) else response.sendRedirect(G_VIEW_URI)
    }
}