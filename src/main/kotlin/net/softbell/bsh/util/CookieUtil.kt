package net.softbell.bsh.util

import org.springframework.web.util.WebUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 쿠키 제어 유틸리티
 */
object CookieUtil {
    @JvmOverloads
    fun create(httpServletResponse: HttpServletResponse, name: String?, value: String?, httpOnly: Boolean?, secure: Boolean?, maxAge: Int?, domain: String? = null) {
        val cookie = Cookie(name, value)
        cookie.secure = secure!!
        cookie.isHttpOnly = httpOnly!!
        cookie.maxAge = maxAge!!
        if (domain != null) cookie.domain = domain
        cookie.path = "/"
        httpServletResponse.addCookie(cookie)
    }

    fun create(httpServletResponse: HttpServletResponse, name: String?, value: String?, secure: Boolean?, maxAge: Int?) {
        create(httpServletResponse, name, value, true, secure, maxAge, null)
    }

    fun create(httpServletResponse: HttpServletResponse, name: String?, value: String?, maxAge: Int?) {
        create(httpServletResponse, name, value, true, false, maxAge, null)
    }

    fun clear(httpServletResponse: HttpServletResponse, name: String?) {
        val cookie = Cookie(name, null)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 0
        httpServletResponse.addCookie(cookie)
    }

    fun getValue(httpServletRequest: HttpServletRequest?, name: String?): String? {
        val cookie = WebUtils.getCookie(httpServletRequest!!, name!!)
        return cookie?.value
    }
}