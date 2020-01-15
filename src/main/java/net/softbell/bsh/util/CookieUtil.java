package net.softbell.bsh.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 쿠키 제어 유틸리티
 */
public class CookieUtil
{
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean httpOnly, Boolean secure, Integer maxAge, String domain)
	{
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        if (domain != null)
        	cookie.setDomain(domain);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
	
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean httpOnly, Boolean secure, Integer maxAge)
	{
        create(httpServletResponse, name, value, httpOnly, secure, maxAge, null);
    }
	
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure, Integer maxAge)
	{
        create(httpServletResponse, name, value, true, secure, maxAge, null);
    }
	
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Integer maxAge)
	{
        create(httpServletResponse, name, value, true, false, maxAge, null);
    }

    public static void clear(HttpServletResponse httpServletResponse, String name)
    {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public static String getValue(HttpServletRequest httpServletRequest, String name)
    {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
