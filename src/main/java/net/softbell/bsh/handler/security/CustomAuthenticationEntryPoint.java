package net.softbell.bsh.handler.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 실패 핸들러
 */
@Slf4j
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	// Global Field
	private String G_API_URI, G_VIEW_URI;

	// 인증이 안 된 요청일 때 진입
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
    		throws IOException, ServletException
    {
    	// Field
    	boolean isAPI = false;
    	
    	// Init
    	if (request.getRequestURI().startsWith("/api/"))
    		isAPI = true;
    	
    	// Redirect
    	if (isAPI)
    		response.sendRedirect(G_API_URI);
    	else
    		response.sendRedirect(G_VIEW_URI);
    }
}
