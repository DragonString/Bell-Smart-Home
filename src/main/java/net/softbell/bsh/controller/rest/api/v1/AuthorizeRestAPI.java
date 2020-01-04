package net.softbell.bsh.controller.rest.api.v1;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.authentication.AuthenticationRequest;
import net.softbell.bsh.dto.authentication.AuthenticationToken;
import net.softbell.bsh.service.MemberService;

@RestController
@RequestMapping("/api/rest/v1/authorize")
public class AuthorizeRestAPI
{
	@Autowired AuthenticationManager authenticationManager;
    @Autowired MemberService userService;
   
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public AuthenticationToken procLogin (@RequestBody AuthenticationRequest authenticationRequest, HttpSession session)
    {
    	// TODO 아직.. 보안은 나중에 추가예정
		String username = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();
		System.out.println("api 로그인 요청 받음!! (" + username + ", " + password + ")" + new Date());
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());
		
		UserDetails user = userService.loadUserByUsername(username);
		if (user == null)
			System.out.println("계정 없음!");
		return new AuthenticationToken(user.getUsername(), user.getAuthorities(), session.getId());
    }
}
