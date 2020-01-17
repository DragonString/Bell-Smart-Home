package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 뷰 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin";
	
	@GetMapping("/member")
    public String dispMember()
	{
        return G_BASE_PATH + "/Member";
    }

	@GetMapping("/node")
    public String dispNode()
	{
        return G_BASE_PATH + "/Node";
    }

	@GetMapping("/permission")
    public String dispPermission()
	{
        return G_BASE_PATH + "/Permission";
    }

	@GetMapping("/center")
    public String dispCenterSetting()
	{
        return G_BASE_PATH + "/CenterSetting";
    }
	
	/*
    // 어드민 회원 관리 페이지
    @GetMapping("/admin/member")
    public String dispMemberList(Model model, Principal principal,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int intPage,
			@RequestParam(value = "count", required = false, defaultValue = "5") int intCount)
    {
		// Auth Check
		if (!memberService.isAdmin(principal.getName()) || memberService.isBan(principal.getName())) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Init
    	FilterModelPrincipal(model, principal);
    	
    	// Process
    	model.addAttribute("memberList", memberService.getMemberList(intPage, intCount));
    	model.addAttribute("memberListCurPage", intPage);
    	model.addAttribute("memberListPageCount", intCount);
    	model.addAttribute("memberListMaxPage", memberService.getMemberMaxPage(intCount));
    	
    	// Return
    	return G_BASE_PATH + "/member";
    }
    
    // 회원 정지 처리
    @RequestMapping(value = "/admin/member/ban", method = RequestMethod.POST)
    public String procMemberBan(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
		// Auth Check
		if (!memberService.isAdmin(principal.getName()) || memberService.isBan(principal.getName())) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procMemberBan(principal, listMemberId, true))
        	return "redirect:/admin/member";
    	else
    		return "redirect:/admin/member?error";
    }
    
    // 회원 정지 해제 처리
    @RequestMapping(value = "/admin/member/unban", method = RequestMethod.POST)
    public String procMemberUnban(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
		// Auth Check
		if (!memberService.isAdmin(principal.getName()) || memberService.isBan(principal.getName())) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procMemberBan(principal, listMemberId, false))
        	return "redirect:/admin/member";
    	else
    		return "redirect:/admin/member?error";
    }
    
    // 회원 권한 상승 처리
    @RequestMapping(value = "/admin/member/addAuth", method = RequestMethod.POST)
    public String procMemberAddAuth(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
		// Auth Check
		if (!memberService.isAdmin(principal.getName()) || memberService.isBan(principal.getName())) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procSetAdmin(principal, listMemberId, true))
        	return "redirect:/admin/member";
    	else
    		return "redirect:/admin/member?error";
    }
    
    // 회원 권한 하강 처리
    @RequestMapping(value = "/admin/member/delAuth", method = RequestMethod.POST)
    public String procMemberDelAuth(Model model, Principal principal,
    		@RequestParam("intMemberId") List<Integer> listMemberId)
    {
		// Auth Check
		if (!memberService.isAdmin(principal.getName()) || memberService.isBan(principal.getName())) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Process
    	if (memberService.procSetAdmin(principal, listMemberId, false))
        	return "redirect:/admin/member";
    	else
    		return "redirect:/admin/member?error";
    }
    
    // 회원탈퇴 처리
    @PostMapping("/admin/member/delete")
    public String execAdminDelete(Principal principal, @RequestParam("intMemberId")List<Integer> listMemberId) {
    	// Check
        if (!memberService.deleteUserList(principal, listMemberId))
        	return "redirect:/admin/member?error";

        return "redirect:/admin/member";
    }*/
}
