package net.softbell.bsh.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 로그인 성공 핸들러
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	MemberService memberService;
	
	private String defaultUrl;

	public LoginSuccessHandler(String defaultUrl)
	{
		setDefaultUrl(defaultUrl);
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException
	{
		// Field
		String strUserId = authentication.getName();
		String strRedirect;
		
		// Init
		strRedirect = request.getHeader("Referer");
		
		// Process
		if (memberService.getMember(strUserId) == null) // 비정상 로그인시
			response.sendRedirect("/logout");
		if (strRedirect == null || strRedirect.isEmpty() || strRedirect.contains("/login"))
			strRedirect = getDefaultUrl();
		//memberService.procLogin(strUserId, ClientData.getClientIP(request), true); // TODO BSNS 프로젝트 그대로 가져온거라 수정 필요함
		
		// Redirect
		response.sendRedirect(strRedirect);
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
}