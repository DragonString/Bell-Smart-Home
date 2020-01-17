package net.softbell.bsh.controller.view;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.request.MemberDto;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 계정 비 인증 뷰 컨트롤러
 */
@Controller
@AllArgsConstructor
public class DmzView
{
	// Global Field
	private final String G_BASE_PATH = "services/dmz";
    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup(Model model, Principal principal)
    {
    	// Init
    	//FilterModelPrincipal(model, principal);

		// Auth Check
		if (principal != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
			return "redirect:/";
		
    	// Return
        return G_BASE_PATH + "/Signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignup(MemberDto memberDTO)
    {
    	// Field
    	long intResult;
    	
    	// Init
    	intResult = memberService.joinUser(memberDTO);
    	
    	// Check
        if (intResult == -1)
        	return "redirect:/signup?error";

        return "redirect:/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response)
    {
    	// Init
    	//FilterModelPrincipal(model, principal);
    	
    	// Auth Check
		if (principal != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
			return "redirect:/";
    	
    	// Return
        return G_BASE_PATH + "/Login";
    }
}