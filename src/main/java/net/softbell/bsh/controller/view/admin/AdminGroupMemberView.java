package net.softbell.bsh.controller.view.admin;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.dto.request.MemberGroupDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.PermissionService;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 회원 그룹 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/group/member/")
public class AdminGroupMemberView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin";
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/group/member";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final PermissionService permissionService;
	private final IotNodeServiceV1 iotNodeService;
	
	@GetMapping("create")
	public String dispGroupCreate(Model model)
	{
		// Field
		
		// Init
		model.addAttribute("listCardMembers", viewDtoConverterService.convGroupMemberCardItems(memberService.getAllMember()));
		
		// Return
		return G_BASE_PATH + "/GroupMemberCreate";
	}
	
	@GetMapping("modify")
	public String dispGroupModify(Model model)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_PATH + "/GroupMemberModify";
	}
	
	@GetMapping("{gid}")
	public String dispGroup(Model model, @PathVariable("gid") Long gid)
	{
		
		// Return
		return G_BASE_PATH + "/GroupMemberInfo";
	}
	
    
	@PostMapping("create")
	public String procGroupCreate(Authentication auth, MemberGroupDto memberGroupDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.createMemberGroup(memberGroupDto);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("modify")
	public String procGroupModify(Authentication auth)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_REDIRECT_URL;
	}
	
	@PostMapping("enable")
	public String procGroupEnable(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_REDIRECT_URL;
	}
	
	@PostMapping("disable")
	public String procGroupDisable(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_REDIRECT_URL;
	}
	
	@PostMapping("remove")
	public String procGroupRemove(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_REDIRECT_URL;
	}
}
