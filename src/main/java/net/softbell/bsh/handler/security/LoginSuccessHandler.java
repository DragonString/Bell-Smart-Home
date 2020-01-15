package net.softbell.bsh.handler.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.softbell.bsh.component.JwtTokenProvider;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.util.ClientData;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 로그인 성공 핸들러
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler
{
	@Autowired
	private MemberService memberService;
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
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
		memberService.procLogin(strUserId, ClientData.getClientIP(request), true);
		jwtTokenProvider.setCookieAuth(response, authentication);
		
		// Redirect
		response.sendRedirect(strRedirect);
	}

	public String getDefaultUrl()
	{
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl)
	{
		this.defaultUrl = defaultUrl;
	}
}