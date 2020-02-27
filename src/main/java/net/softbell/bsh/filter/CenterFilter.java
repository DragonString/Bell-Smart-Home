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

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.service.CenterService;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 오버라이드 필터
 */
@RequiredArgsConstructor
@Component
@Order(1)
public class CenterFilter implements Filter
{
	private final CenterService centerService;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException
	{
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
    		throws IOException, ServletException
    {
    	// Anything..
    	servletRequest.setAttribute("setting", centerService.getSetting());
    	
    	// Next Filter
    	filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {
    	
    }
}
