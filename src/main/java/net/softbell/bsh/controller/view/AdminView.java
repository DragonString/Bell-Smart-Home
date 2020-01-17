package net.softbell.bsh.controller.view;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminView
{
	// Global Field
	private final String G_BASE_PATH = "services/admin";
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
	
	@GetMapping("/member")
    public String dispMember(Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int intPage,
			@RequestParam(value = "count", required = false, defaultValue = "100") int intCount)
	{
		// Field
		Page<Member> pageMember;
		
		// Init
		pageMember = memberService.getMemberList(intPage, intCount);
		
		// Load
		model.addAttribute("listMember", pageMember);
		
		// Return
        return G_BASE_PATH + "/Member";
    }

	@GetMapping("/node")
    public String dispNode(Model model,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int intPage,
			@RequestParam(value = "count", required = false, defaultValue = "100") int intCount)
	{
		// Field
		Page<Node> pageNode;
		
		// Init
		pageNode = iotNodeService.getAllNodes(intPage, intCount);
		
		// Process
		model.addAttribute("listNode", pageNode);
		
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
}
