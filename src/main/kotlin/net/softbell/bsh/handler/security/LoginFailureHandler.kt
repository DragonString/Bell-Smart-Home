package net.softbell.bsh.handler.security

import net.softbell.bsh.service.MemberService
import net.softbell.bsh.util.ClientData.getClientIP
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 로그인 실패 핸들러
 */
class LoginFailureHandler(defaultUrl: String?) : AuthenticationFailureHandler {
    @Autowired private lateinit var memberService: MemberService

    private var defaultUrl: String? = null

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException?) {
        // Field
        val strUserId = request.getParameter("userId")

        // Process
        memberService!!.procLogin(strUserId, getClientIP(request), false)

        // Redirect
        response.sendRedirect(getDefaultUrl())
    }

    fun getDefaultUrl(): String? {
        return defaultUrl
    }

    fun setDefaultUrl(defaultUrl: String?) {
        this.defaultUrl = defaultUrl
    }

    init {
        setDefaultUrl(defaultUrl)
    }
}