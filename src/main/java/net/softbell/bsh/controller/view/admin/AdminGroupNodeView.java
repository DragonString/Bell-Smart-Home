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
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.domain.entity.NodeGroupItem;
import net.softbell.bsh.dto.request.NodeGroupDto;
import net.softbell.bsh.dto.request.NodeGroupPermissionDto;
import net.softbell.bsh.dto.view.admin.group.NodeGroupInfoCardDto;
import net.softbell.bsh.dto.view.admin.group.NodeGroupPermissionCardDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.PermissionService;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 노드 그룹 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/group/node")
public class AdminGroupNodeView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin/group";
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/group/node";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final PermissionService permissionService;
	private final IotNodeServiceV1 iotNodeService;
	

	@GetMapping()
    public String dispGroupNode(Model model)
	{
		// Load
		model.addAttribute("listCardGroups", viewDtoConverterService.convNodeGroupSummaryCards(permissionService.getAllNodeGroup()));
		
		// Return
        return G_BASE_PATH + "/NodeGroup";
    }
	
	@GetMapping("/create")
	public String dispGroupCreate(Model model)
	{
		// Field
		
		// Init
		model.addAttribute("listCardNodes", viewDtoConverterService.convGroupNodeCardItems(iotNodeService.getAllNodes()));
		
		// Return
		return G_BASE_PATH + "/NodeGroupCreate";
	}
	
	@GetMapping("/modify/{gid}")
	public String dispGroupModify(Model model, @PathVariable("gid") Long gid)
	{
		// Field
		List<Node> listNode;
		NodeGroup nodeGroup;
		
		// Init
		listNode = iotNodeService.getAllNodes();
		nodeGroup = permissionService.getNodeGroup(gid);
		
		// Process
		for (NodeGroupItem entity : nodeGroup.getNodeGroupItems())
			listNode.remove(entity.getNode());
		
		// View
		model.addAttribute("cardGroup", new NodeGroupInfoCardDto(permissionService.getNodeGroup(gid)));
		model.addAttribute("listCardNodes", viewDtoConverterService.convGroupNodeCardItems(listNode));
		
		// Return
		return G_BASE_PATH + "/NodeGroupModify";
	}
	
	@GetMapping("/{gid}")
	public String dispGroup(Model model, @PathVariable("gid") Long gid)
	{
		// Field
		
		// Init
		model.addAttribute("cardPermission", new NodeGroupPermissionCardDto(permissionService.getAllMemberGroup()));
		model.addAttribute("cardGroup", new NodeGroupInfoCardDto(permissionService.getNodeGroup(gid)));
		
		// Return
		return G_BASE_PATH + "/NodeGroupInfo";
	}
	
    
	@PostMapping("/create")
	public String procGroupCreate(Authentication auth, NodeGroupDto nodeGroupDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.createNodeGroup(nodeGroupDto);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("/modify/{gid}")
	public String procGroupModify(Authentication auth, @PathVariable("gid") Long gid, NodeGroupDto nodeGroupDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.modifyNodeGroup(gid, nodeGroupDto);
		
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
		isSuccess = permissionService.enableNodeGroup(listGid);
		
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
		isSuccess = permissionService.disableNodeGroup(listGid);
		
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
		isSuccess = permissionService.deleteNodeGroup(listGid);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL;
		else
			return G_BASE_REDIRECT_URL + "?err";
	}
	
	@PostMapping("/permission/add/{gid}")
	public String addPermission(@PathVariable("gid") Long gid, NodeGroupPermissionDto nodeGroupPermissionDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = permissionService.addNodePermission(gid, nodeGroupPermissionDto);
		
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
