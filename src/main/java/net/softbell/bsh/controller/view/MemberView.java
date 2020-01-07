package net.softbell.bsh.controller.view;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.member.MemberDTO;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 뷰 컨트롤러
 */
@Controller
@AllArgsConstructor
//@RequestMapping("/member")
public class MemberView {//extends ControllerFilter {
	// Global Field
	private final String G_BASE_PATH = "services/member";
    private MemberService memberService;

    // 메인 페이지
    @GetMapping("/member")
    public String index() {
        return G_BASE_PATH + "/index";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return G_BASE_PATH + "/myinfo";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return G_BASE_PATH + "/admin";
    }
    
    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup(Model model, Principal principal) {
    	// Init
    	//FilterModelPrincipal(model, principal);

		// Auth Check
		if (principal != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
			return "redirect:/";
		
    	// Return
        return G_BASE_PATH + "/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignup(MemberDTO memberDTO) {
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
    public String dispLogin(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
    	// Init
    	//FilterModelPrincipal(model, principal);
    	
    	// Return
        return G_BASE_PATH + "/login";
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    public String dispDenied(Model model, Principal principal) {
    	// Init
    	//FilterModelPrincipal(model, principal);
    	
    	// Return
        return G_BASE_PATH + "/denied";
    }

    /*// 내 정보 페이지
    @GetMapping("/member/info")
    public String dispMyInfo(Model model, Principal principal) {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Init
    	FilterModelPrincipal(model, principal);
		
    	// Process
    	model.addAttribute("memberInfo", memberService.getInfo(principal));
    	if (memberService.checkDelete(principal.getName()))
    			model.addAttribute("checkDelete", "1");
    	
    	// Return
        return G_BASE_PATH + "/myinfo";
    }
    
    // 내 정보 수정 페이지
    @GetMapping("/member/modify")
    public String dispMyInfoModify(Model model, Principal principal) {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Init
    	FilterModelPrincipal(model, principal);
		
    	// Process
    	model.addAttribute("memberInfo", memberService.getInfo(principal));
   	
    	// Return
        return G_BASE_PATH + "/modify";
    }
    
    // 내 정보 수정 처리
    @RequestMapping(value = "/member/modify", method = RequestMethod.POST)
    public String procMyInfoModify(Model model, Principal principal,
    		@RequestParam(value = "curPassword") String strCurPassword,
    		@RequestParam(value = "modPassword") String strModPassword) {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Field
    	Member member;
		
    	// Init
    	FilterModelPrincipal(model, principal);
    	member = memberService.modifyInfo(principal, strCurPassword, strModPassword);
    	
    	// Process
    	if (member == null)
			return "redirect:/member/modify?error";
    	
    	// Return
        return G_LOGOUT_REDIRECT_URL;
    }

    // 회원탈퇴 처리
    @PostMapping("/member/delete")
    public String execDelete(MemberInfoDTO memberDto) {
    	// Check
        if (!memberService.deleteUser(memberDto))
        	return "redirect:/member/info?error";

        return "redirect:/logout";
    }
    
    // 회원탈퇴 처리
    @PostMapping("/admin/member/delete")
    public String execAdminDelete(Principal principal, @RequestParam("intMemberId")List<Integer> listMemberId) {
    	// Check
        if (!memberService.deleteUserList(principal, listMemberId))
        	return "redirect:/admin/member?error";

        return "redirect:/admin/member";
    }

    // 로그인 로그 페이지
    @GetMapping("/member/log")
    public String dispLoginLog(Model model, Principal principal,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int intPage,
			@RequestParam(value = "count", required = false, defaultValue = "20") int intCount)
    {
		// Auth Check
		if (memberService.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
			return G_LOGOUT_REDIRECT_URL;
		
    	// Init
    	FilterModelPrincipal(model, principal);
    	
    	// Exception
    	if (intPage < 1)
    		intPage = 1;
    	if (intCount < 1)
    		intCount = 1;
    	
    	// Process
    	model.addAttribute("logList", memberService.getLoginLog(principal, intPage, intCount));
		model.addAttribute("logCurPage", intPage);
		model.addAttribute("logPageCount", intCount);
    	model.addAttribute("logMaxPage", memberService.getLoginLogMaxPage(principal, intCount));
    	
    	// Return
        return G_BASE_PATH + "/log";
    }

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
    }*/
}