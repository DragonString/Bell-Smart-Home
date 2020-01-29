package net.softbell.bsh.controller.view.admin;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.dto.view.admin.NodeManageInfoCardDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.ViewDtoConverterService;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 노드 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/node/")
public class AdminNodeView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin";
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/node";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
    
    // 노드 정보 수정페이지 출력
    @GetMapping("modify/{id}")
    public String dispNodeModify(Model model, Principal principal, @PathVariable("id") long nodeId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
		Node node;
		
		// Init
		node = iotNodeService.getNode(nodeId);
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
		model.addAttribute("cardNodeInfo", new NodeManageInfoCardDto(node));
		model.addAttribute("listCardNodeItems", viewDtoConverterService.convNodeManageItemCards(node.getNodeItems()));
		
		// Return
        return G_BASE_PATH + "/NodeModify";
    }
	
	// 노드 비활성화 처리
    @PostMapping("disable")
    public String procNodeDisable(Model model, Principal principal,
    		@RequestParam("intNodeId") int intNodeId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Log
    	log.info(BellLog.getLogHead() + intNodeId + " 노드 비활성화 ");
    	
    	// Process
    	if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.DISABLE))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
	// 노드 활성화 처리
    @PostMapping("enable")
    public String procNodeEnable(Model model, Principal principal,
    		@RequestParam("intNodeId") int intNodeId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Log
    	log.info(BellLog.getLogHead() + intNodeId + " 노드 활성화 ");
    	
    	// Process
    	if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.ENABLE))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 노드 제한 처리
    @PostMapping("reject")
    public String procNodeReject(Model model, Principal principal,
    		@RequestParam("intNodeId") int intNodeId)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Log
    	log.info(BellLog.getLogHead() + intNodeId + " 노드 승인 거절 ");
    	
    	// Process
    	if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.REJECT))
        	return G_BASE_REDIRECT_URL;
    	else
    		return G_BASE_REDIRECT_URL + "?error";
    }
    
    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/{id}")
    public String procNodeModify(Model model, Principal principal, @PathVariable("id") long nodeId,
    							@RequestParam("alias") String alias)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (iotNodeService.setNodeAlias(nodeId, alias))
    		return G_BASE_REDIRECT_URL + "/modify/" + nodeId;
    	else
    		return G_BASE_REDIRECT_URL + "/modify/" + nodeId + "?error";
    }
    
    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/item/{id}")
    public String procNodeItemModify(Model model, Principal principal, @PathVariable("id") long nodeItemId,
    							@RequestParam("id") long nodeId,
    							@RequestParam("alias") String alias)
    {
    	// Field
    	Member member = memberService.getAdminMember(principal.getName());
    	
    	// Exception
    	if (member == null)
    		return G_LOGOUT_REDIRECT_URL;
    	
    	// Process
    	if (iotNodeService.setNodeItemAlias(nodeItemId, alias))
    		return G_BASE_REDIRECT_URL + "/modify/" + nodeId;
    	else
    		return G_BASE_REDIRECT_URL + "/modify/" + nodeId + "?error";
    }
}
