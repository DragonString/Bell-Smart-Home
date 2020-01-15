package net.softbell.bsh.filter.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.AllArgsConstructor;
import net.softbell.bsh.component.JwtTokenProvider;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 처리 필터
 */
@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean
{
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    		throws IOException, ServletException
    {
    	// Field
        String token;
        
        // Init
        token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
        // Process
        if (token != null && jwtTokenProvider.validateToken(token))
        {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            if (!jwtTokenProvider.isApiMode((HttpServletRequest)request)) // API 모드가 아니면
            	jwtTokenProvider.setCookieAuth((HttpServletResponse) response, auth); // 인증 토큰 만료 기간 연장
        }
        
        // Filter
        filterChain.doFilter(request, response);
    }
}
