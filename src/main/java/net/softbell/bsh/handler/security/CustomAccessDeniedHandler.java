package net.softbell.bsh.handler.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
	// Global Field
	private String G_API_URI, G_VIEW_URI;
	
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
    		throws IOException
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
