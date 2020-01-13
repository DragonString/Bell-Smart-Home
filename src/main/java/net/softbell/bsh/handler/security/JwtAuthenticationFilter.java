package net.softbell.bsh.handler.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean
{
    private final JwtTokenProvider jwtTokenProvider;

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    		throws IOException, ServletException
    {
    	// Field
        String token;
        
        // Init
        token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        System.out.println("------------- JWT 필터 진입 ---------------" + ((HttpServletRequest) request).getRequestURI());
        
        // Process
        if (token != null && jwtTokenProvider.validateToken(token))
        {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        // Filter
        filterChain.doFilter(request, response);
    }
}
