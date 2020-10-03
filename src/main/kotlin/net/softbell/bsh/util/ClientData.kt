package net.softbell.bsh.util

import javax.servlet.http.HttpServletRequest

/**
 * @author : Bell(bell@softbell.net)
 * @description : 클라이언트 데이터 처리 라이브러리
 */
object ClientData {
    fun getClientIP(request: HttpServletRequest): String {
        // Field
        var ipv4 = request.getHeader("X-Forwarded-For")

        // Load
        if (ipv4 == null || ipv4.isEmpty() || "unknown".equals(ipv4, ignoreCase = true)) ipv4 = request.getHeader("Proxy-Client-IP")
        if (ipv4 == null || ipv4.isEmpty() || "unknown".equals(ipv4, ignoreCase = true)) ipv4 = request.getHeader("WL-Proxy-Client-IP")
        if (ipv4 == null || ipv4.isEmpty() || "unknown".equals(ipv4, ignoreCase = true)) ipv4 = request.getHeader("HTTP_CLIENT_IP")
        if (ipv4 == null || ipv4.isEmpty() || "unknown".equals(ipv4, ignoreCase = true)) ipv4 = request.getHeader("HTTP_X_FORWARDED_FOR")
        if (ipv4 == null || ipv4.isEmpty() || "unknown".equals(ipv4, ignoreCase = true)) ipv4 = request.remoteAddr

        // Return
        return ipv4
    }
}