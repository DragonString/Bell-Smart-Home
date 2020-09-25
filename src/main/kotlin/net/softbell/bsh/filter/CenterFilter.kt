package net.softbell.bsh.filter

import lombok.RequiredArgsConstructor
import net.softbell.bsh.service.CenterService
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 오버라이드 필터
 */
@RequiredArgsConstructor
@Component
@Order(1)
class CenterFilter : Filter {
    private val centerService: CenterService? = null
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        // Anything..
        servletRequest.setAttribute("setting", centerService.getSetting())

        // Next Filter
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {}
}