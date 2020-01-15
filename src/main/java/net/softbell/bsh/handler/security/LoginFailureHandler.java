package net.softbell.bsh.handler.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 로그인 실패 핸들러
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	MemberService memberService;
	
	private String defaultUrl;

	public LoginFailureHandler(String defaultUrl)
	{
		setDefaultUrl(defaultUrl);
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// Field
		String strUserId = request.getParameter("username");
		
		// Process
		//memberService.procLogin(strUserId, ClientData.getClientIP(request), false); // TODO BSNS 프로젝트 그대로 가져온거라 수정 필요함
		
		// Redirect
		response.sendRedirect(getDefaultUrl());
	}
	
	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}
