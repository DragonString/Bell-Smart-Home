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
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.MemberGroupItem;
import net.softbell.bsh.dto.request.MemberGroupDto;
import net.softbell.bsh.dto.request.MemberGroupPermissionDto;
import net.softbell.bsh.dto.view.admin.group.MemberGroupInfoCardDto;
import net.softbell.bsh.dto.view.admin.group.MemberGroupPermissionCardDto;
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
@RequestMapping("/admin/group/member")
public class AdminGroupMemberView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin/group";
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/group/member";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final PermissionService permissionService;
	private final IotNodeServiceV1 iotNodeService;

	
	@GetMapping()
    public String dispGroupMember(Model model)
	{
		// Load
		model.addAttribute("listCardGroups", viewDtoConverterService.convMemberGroupSummaryCards(permissionService.getAllMemberGroup()));
		
		// Return
        return G_BASE_PATH + "/MemberGroup";
    }
	
	@GetMapping("/create")
	public String dispGroupCreate(Model model)
	{
		// Field
		
		// Init
		model.addAttribute("listCardMembers", viewDtoConverterService.convGroupMemberCardItems(memberService.getAllMember()));
		
		// Return
		return G_BASE_PATH + "/MemberGroupCreate";
	}
	
	@GetMapping("/modify/{gid}")
	public String dispGroupModify(Model model, @PathVariable("gid") Long gid)
	{
		// Field
		List<Member> listMember;
		MemberGroup memberGroup;
		
		// Init
		listMember = memberService.getAllMember();
		memberGroup = permissionService.getMemberGroup(gid);
		
		// Process
		for (MemberGroupItem entity : memberGroup.getMemberGroupItems())
			listMember.remove(entity.getMember());
		
		// View
		model.addAttribute("cardGroup", new MemberGroupInfoCardDto(permissionService.getMemberGroup(gid)));
		model.addAttribute("listCardMembers", viewDtoConverterService.convGroupMemberCardItems(listMember));
		
		// Return
		return G_BASE_PATH + "/MemberGroupModify";
	}
	
	@GetMapping("/{gid}")
	public String dispGroup(Model model, @PathVariable("gid") Long gid)
	{
		// Field
		
		// Init
		model.addAttribute("cardPermission", new MemberGroupPermissionCardDto(permissionService.getAllNodeGroup()));
		model.addAttribute("cardGroup", new MemberGroupInfoCardDto(permissionService.getMemberGroup(gid)));
		
		// Return
		return G_BASE_PATH + "/MemberGroupInfo";
	}
	
    
	@PostMapping("/create")
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
	
	@PostMapping("/modify/{gid}")
	public String procGroupModify(Authentication auth, @PathVariable("gid") Long gid, MemberGroupDto memberGroupDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.modifyMemberGroup(gid, memberGroupDto);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL + "/" + gid;
		else
			return G_BASE_REDIRECT_URL + "/" + gid + "?err";
	}
	
	@PostMapping("/enable")
	public String procGroupEnable(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.enableMemberGroup(listGid);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("/disable")
	public String procGroupDisable(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.disableMemberGroup(listGid);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("/delete")
	public String procGroupDelete(Authentication auth, @RequestParam("gid") List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.deleteMemberGroup(listGid);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("/permission/add/{gid}")
	public String addPermission(@PathVariable("gid") Long gid, MemberGroupPermissionDto memberGroupPermissionDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.addMemberPermission(gid, memberGroupPermissionDto);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL + "/" + gid;
		else
			return G_BASE_REDIRECT_URL + "/" + gid + "?err";
	}
	
	@PostMapping("/permission/delete/{gid}")
	public String deletePermission(@PathVariable("gid") Long gid, @RequestParam("pid") Long pid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.deleteGroupPermission(pid);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL + "/" + gid;
		else
			return G_BASE_REDIRECT_URL + "/" + gid + "?err";
	}
}
