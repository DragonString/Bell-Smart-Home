package net.softbell.bsh.handler.security

import net.softbell.bsh.component.JwtTokenProvider
import net.softbell.bsh.config.CustomConfig
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.util.ClientData.getClientIP
import net.softbell.bsh.util.CookieUtil.create
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author : Bell(bell@softbell.net)
 * @description : 로그인 성공 핸들러
 */
class LoginSuccessHandler(val defaultUrl: String) : AuthenticationSuccessHandler {
    @Autowired private lateinit var memberService: MemberService
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider


    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        // Init
        val strUserId = authentication.name
        var strRedirect = request.getHeader("Referer")
        val strAutoLogin = request.getParameter("autoLogin")

        if (strAutoLogin.equals("true", ignoreCase = true))
            create(response, CustomConfig.AUTO_LOGIN_COOKIE_NAME, "1", 60 * 60 * 24 * 7) // 자동 로그인 쿠키 생성

        // Process
        if (memberService.getMember(strUserId) == null) // 비정상 로그인시
            response.sendRedirect("/logout")
        if (strRedirect == null || strRedirect.isEmpty() || strRedirect.contains("/login"))
            strRedirect = defaultUrl
        memberService.procLogin(strUserId, getClientIP(request), true)
        jwtTokenProvider.setCookieAuth(request, response, authentication)

        // Redirect
        response.sendRedirect(strRedirect)
    }
}