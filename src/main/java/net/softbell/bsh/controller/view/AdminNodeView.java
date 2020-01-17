package net.softbell.bsh.controller.view;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 회원 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/node/")
public class AdminNodeView
{
	// Global Field
	private final String G_BASE_REDIRECT_URL = "redirect:/admin/node";
	private final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
	
	// 회원 승인 처리
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
    
	// 회원 승인 처리
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
    
    // 회원 승인 처리
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
}
