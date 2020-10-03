package net.softbell.bsh.handler.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author : Bell(bell@softbell.net)
 * @description : 접근 제한 핸들러
 */
@Component
class CustomAccessDeniedHandler(
        private val API_URI: String = "",
        private val VIEW_URI: String = ""
) : AccessDeniedHandler {
    @Throws(IOException::class)
    override fun handle(request: HttpServletRequest, response: HttpServletResponse, exception: AccessDeniedException) {
        // Field
        var isAPI = false

        // Init
        if (request.requestURI.startsWith("/api/"))
            isAPI = true

        // Redirect
        if (isAPI)
            response.sendRedirect(API_URI)
        else
            response.sendRedirect(VIEW_URI)
    }
}