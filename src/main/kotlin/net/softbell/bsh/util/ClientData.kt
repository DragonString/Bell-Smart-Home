package net.softbell.bsh.util

import javax.servlet.http.HttpServletRequest
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 클라이언트 데이터 처리 라이브러리
 */
object ClientData {
    fun getClientIP(request: HttpServletRequest): String? {
        // Field
        var ip = request.getHeader("X-Forwarded-For")

        // Load
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) ip = request.getHeader("Proxy-Client-IP")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) ip = request.getHeader("WL-Proxy-Client-IP")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) ip = request.getHeader("HTTP_CLIENT_IP")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) ip = request.remoteAddr

        // Return
        return ip
    }
}