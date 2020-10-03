package net.softbell.bsh.filter

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : request 최초, response 최후에 동작하는 필터 클래스
 */
@Component
@Order(1)
class BaseFilter : Filter {
    //	private final LocaleResolver localeResolver;
    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        // Anything..

        // Next Filter
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {}
}