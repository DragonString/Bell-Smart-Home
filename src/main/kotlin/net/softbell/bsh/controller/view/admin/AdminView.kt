package net.softbell.bsh.controller.view.admin;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.dto.request.CenterSettingDto;
import net.softbell.bsh.dto.view.admin.CenterSettingSummaryCardDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.CenterService;
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
	private final String G_BASE_REDIRECT_URL = "redirect:/admin";
	private final String G_INDEX_REDIRECT_URL = "redirect:/";
	
	private final ViewDtoConverterService viewDtoConverterService;
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
	private final CenterService centerService;
	
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
		// Exception
		if (centerService.getSetting().getIotNode() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		Page<Node> pageNode;
		
		// Init
		pageNode = iotNodeService.getAllNodes(intPage, intCount);
		
		// Process
		model.addAttribute("listCardNodes", viewDtoConverterService.convNodeManageSummaryCards(pageNode.getContent()));
		
		// Return
        return G_BASE_PATH + "/Node";
    }

	@GetMapping("/center")
    public String dispCenterSetting(Model model)
	{
		// Process
		model.addAttribute("cardCenterSetting", new CenterSettingSummaryCardDto(centerService.loadSetting()));
		model.addAttribute("cardCenterSettingDefault", new CenterSettingSummaryCardDto(centerService.getSetting()));
		
		// Return
        return G_BASE_PATH + "/CenterSetting";
    }

	@GetMapping("/center/modify")
    public String dispCenterSettingModify(Model model)
	{
		// Process
		model.addAttribute("cardCenterSetting", new CenterSettingSummaryCardDto(centerService.loadSetting()));
		
		// Return
        return G_BASE_PATH + "/CenterSettingModify";
    }
	
	@PostMapping("/center/modify")
	public String modifyCenterSetting(Model model, CenterSettingDto centerSettingDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = centerService.modifyCenterSetting(centerSettingDto);
		
		// Return
		if (isSuccess)
			return G_BASE_REDIRECT_URL + "/center";
		else
			return G_BASE_REDIRECT_URL + "/center/modify?err";
	}
}
