package net.softbell.bsh.util

import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author : Bell(bell@softbell.net)
 * @description : 쿠키 제어 유틸리티
 */
object CookieUtil {
    fun create(httpServletResponse: HttpServletResponse, name: String, value: String, maxAge: Int, httpOnly: Boolean = true, secure: Boolean = false, domain: String? = null) {
        val cookie = Cookie(name, value)
        cookie.secure = secure
        cookie.isHttpOnly = httpOnly
        cookie.maxAge = maxAge
        if (domain != null)
            cookie.domain = domain
        cookie.path = "/"
        httpServletResponse.addCookie(cookie)
    }

    fun clear(httpServletResponse: HttpServletResponse, name: String) {
        val cookie = Cookie(name, null)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 0
        httpServletResponse.addCookie(cookie)
    }

    fun getValue(httpServletRequest: HttpServletRequest, name: String): String? {
        val cookie = WebUtils.getCookie(httpServletRequest, name)

        return cookie?.value
    }
}