package net.softbell.bsh.controller.view;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberLoginLog;
import net.softbell.bsh.dto.view.MemberProfileCardDto;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 계정 인증 뷰 컨트롤러
 */
@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class AuthView
{
	// Global Field
	private final String G_BASE_PATH = "services/auth";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	private final ViewDtoConverterService viewDtoConverterService;
    private final MemberService memberService;

    // 내 정보 페이지
    @GetMapping("/profile")
    public String dispMyInfo(Model model, Principal principal)
    {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
		// Field
		Member member;
		
    	// Init
		member = memberService.getMember(principal.getName());
		
    	// Process
    	model.addAttribute("cardMemberProfile", new MemberProfileCardDto(member));
//    	if (memberService.checkDelete(principal.getName()))
//    			model.addAttribute("checkDelete", "1");
    	
    	// Return
        return G_BASE_PATH + "/Profile";
    }
    
    // 내 정보 수정 페이지
    @GetMapping("/modify")
    public String dispMyInfoModify(Model model, Principal principal)
    {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Return
        return G_BASE_PATH + "/Modify";
    }
    
    // 내 정보 수정 처리
    @PostMapping("/modify")
    public String procMyInfoModify(Model model, Principal principal,
						    		@RequestParam("curPassword") String strCurPassword,
						    		@RequestParam("modPassword") String strModPassword)
    {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Field
    	Member member;
		
    	// Init
    	member = memberService.modifyInfo(principal, strCurPassword, strModPassword);
    	
    	// Process
    	if (member == null)
			return "redirect:/member/modify?error";
    	
    	// Return
        return G_LOGOUT_REDIRECT_URL;
    }
/*
    // 회원탈퇴 처리
    @PostMapping("/delete")
    public String execDelete(MemberInfoDTO memberDto)
    {
    	// Check
        if (!memberService.deleteUser(memberDto))
        	return "redirect:/member/info?error";

        return "redirect:/logout";
    }
*/
    // 로그인 로그 페이지
    @GetMapping("/log")
    public String dispLoginLog(Model model, Principal principal,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int intPage,
			@RequestParam(value = "count", required = false, defaultValue = "100") int intCount)
    {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Exception
    	if (intPage < 1)
    		intPage = 1;
    	if (intCount < 1)
    		intCount = 1;
    	
    	// Field
    	Page<MemberLoginLog> pageMemberLoginLog;
    	
    	// Init
    	pageMemberLoginLog = memberService.getLoginLog(principal, intPage, intCount);
    	
    	// Process
    	model.addAttribute("listCardActivityLogs", viewDtoConverterService.convMemberActivityLogCards(pageMemberLoginLog.getContent()));
//		model.addAttribute("logCurPage", intPage);
//		model.addAttribute("logPageCount", intCount);
//    	model.addAttribute("logMaxPage", memberService.getLoginLogMaxPage(principal, intCount));
    	
    	// Return
        return G_BASE_PATH + "/LoginLog";
    }
}