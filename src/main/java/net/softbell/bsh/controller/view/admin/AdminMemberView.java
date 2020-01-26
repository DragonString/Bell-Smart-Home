package net.softbell.bsh.controller.view.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 회원 관리 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/admin/member/")
public class AdminMemberView
{
	// Global Field
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/member";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	private final MemberService memberService;
	
	// 회원 승인 처리
    @PostMapping("approvalNode")
    public String procNodeApproval(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (memberService.procMemberApproval(principal, listMemberId, true, false))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
	// 회원 승인 처리
    @PostMapping("approvalMember")
    public String procMemberApproval(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (memberService.procMemberApproval(principal, listMemberId, true, true))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원 승인 처리
    @PostMapping("refusal")
    public String procMemberRefusal(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (memberService.procMemberApproval(principal, listMemberId, false, true))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원 정지 처리
    @PostMapping("ban")
    public String procMemberBan(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (memberService.procMemberBan(principal, listMemberId, true))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원 정지 해제 처리
    @PostMapping("unban")
    public String procMemberUnban(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procMemberBan(principal, listMemberId, false))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원 권한 상승 처리
    @PostMapping("addAuth")
    public String procMemberAddAuth(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (memberService.procSetAdmin(principal, listMemberId, true))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원 권한 하강 처리
    @PostMapping("delAuth")
    public String procMemberDelAuth(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procSetAdmin(principal, listMemberId, false))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 회원탈퇴 처리
    @PostMapping("delete")
    public String execAdminDelete(Principal principal, @RequestParam("intMemberId")List<Integer> listMemberId)
    {
    	// Check
        if (!memberService.deleteUserList(principal, listMemberId))
        	return G_BASE_REDIRECT_URL + "?error";

        return G_BASE_REDIRECT_URL;
    }
}
