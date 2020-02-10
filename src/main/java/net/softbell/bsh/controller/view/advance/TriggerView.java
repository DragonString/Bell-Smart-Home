package net.softbell.bsh.controller.view.advance;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.dto.request.IotTriggerDto;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.iot.service.v1.IotTriggerServiceV1;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/trigger")
public class TriggerView
{
	// Global Field
	private final String G_BASE_PATH = "services/advance";
	private final ViewDtoConverterService viewDtoConverterService;
	private final IotNodeServiceV1 iotNodeService;
	private final IotTriggerServiceV1 iotTriggerService;
	private final IotActionServiceV1 iotActionService;
	
	@GetMapping()
    public String dispIndex(Model model, Authentication auth)
	{
		// Field
		List<NodeTrigger> listTrigger;
		
		// Init
		listTrigger = iotTriggerService.getAllTriggers(auth);
		
		// Process
		model.addAttribute("listCardTriggers", viewDtoConverterService.convTriggerSummaryCards(listTrigger));
		
		// Return
        return G_BASE_PATH + "/Trigger";
    }
	
	@GetMapping("/create")
	public String dispCreate(Model model, Authentication auth)
	{
		// Field
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeAction = iotActionService.getAllNodeActions(auth);
		
		// Process
		model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction));
		model.addAttribute("listCardNodeItems", viewDtoConverterService.convTriggerItemCards(iotNodeService.getAllNodeItems(auth)));
		
		// Return
		return G_BASE_PATH + "/TriggerCreate";
	}
	
	@GetMapping("/{id}")
	public String dispTrigger(Model model, @PathVariable("id") Long triggerId)
	{
		// Field
		
		// Init
		
		// Return
		return G_BASE_PATH + "/TriggerModify";
	}
	
	@PostMapping("/create")
	public String procCreate(Authentication auth, IotTriggerDto iotTriggerDto)
	{
		// Field
		boolean isSuccess;
		
		// Process
		isSuccess = iotTriggerService.createTrigger(auth, iotTriggerDto);

		// Return
		if (isSuccess)
			return "redirect:/trigger";
		else
			return "redirect:/trigger?err";
	}
	
	@PostMapping("/modify")
	public String procModify(Authentication auth)
	{
		// Field
		
		// Init
		
		// Process
		
		// Return
		return "redirect:/trigger/" + 1;
	}
}
