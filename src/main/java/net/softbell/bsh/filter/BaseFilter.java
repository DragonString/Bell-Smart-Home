package net.softbell.bsh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : request 최초, response 최후에 동작하는 필터 클래스
 */
@AllArgsConstructor
@Component
@Order(1)
public class BaseFilter implements Filter
{
//	private final LocaleResolver localeResolver;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException
	{
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    		throws IOException, ServletException
    {
    	// Anything..
    	
    	// Next Filter
    	filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {
    	
    }
}
