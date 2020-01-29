package net.softbell.bsh.controller.view.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.CenterSetting;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.dto.view.admin.CenterSettingSummaryCardDto;
import net.softbell.bsh.iot.service.v1.IotCenterServiceV1;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.ViewDtoConverterService;

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
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
	private final IotCenterServiceV1 iotCenterService;
	
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
		model.addAttribute("listCardMembers", viewDtoConverterService.convMemberSummaryCards(pageMember.getContent()));
		
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
		model.addAttribute("listCardNodes", viewDtoConverterService.convNodeManageSummaryCards(pageNode.getContent()));
		
        return G_BASE_PATH + "/Node";
    }

	@GetMapping("/permission")
    public String dispPermission(Model model)
	{
        return G_BASE_PATH + "/Permission";
    }

	@GetMapping("/center")
    public String dispCenterSetting(Model model)
	{
		// Field
		CenterSetting centerSetting;
		
		// Init
		centerSetting = iotCenterService.getSetting();
		
		// Process
		model.addAttribute("cardCenterSetting", new CenterSettingSummaryCardDto(centerSetting));
		
		// Return
        return G_BASE_PATH + "/CenterSetting";
    }
}
