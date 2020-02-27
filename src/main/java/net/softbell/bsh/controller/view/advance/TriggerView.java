package net.softbell.bsh.controller.view.advance;

import java.util.ArrayList;
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
import net.softbell.bsh.domain.entity.NodeTriggerAction;
import net.softbell.bsh.dto.request.IotTriggerDto;
import net.softbell.bsh.dto.view.advance.TriggerInfoCardDto;
import net.softbell.bsh.dto.view.general.ActionSummaryCardDto;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;
import net.softbell.bsh.iot.service.v1.IotTriggerServiceV1;
import net.softbell.bsh.service.CenterService;
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
	private final String G_INDEX_REDIRECT_URL = "redirect:/";
	
	private final ViewDtoConverterService viewDtoConverterService;
	private final IotTriggerServiceV1 iotTriggerService;
	private final IotActionServiceV1 iotActionService;
	private final CenterService centerService;
	
	@GetMapping()
    public String dispIndex(Model model, Authentication auth)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
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
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeAction = iotActionService.getAllNodeActions(auth);
		
		// Process
		model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction));
		//model.addAttribute("listCardNodeItems", viewDtoConverterService.convTriggerItemCards(iotNodeService.getAllNodeItems(auth)));
		
		// Return
		return G_BASE_PATH + "/TriggerCreate";
	}
	
	@GetMapping("/{id}")
	public String dispTrigger(Model model, Authentication auth, @PathVariable("id") Long triggerId)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		NodeTrigger nodeTrigger;
		List<ActionSummaryCardDto> listCardActionsAll;
		List<ActionSummaryCardDto> listCardActionsOccurAndRestore;
		List<ActionSummaryCardDto> listCardActionsOccur;
		List<ActionSummaryCardDto> listCardActionsRestore;
		List<ActionSummaryCardDto> listCardActionsError;
		
		// Init
		nodeTrigger = iotTriggerService.getTrigger(auth, triggerId);
		listCardActionsAll = new ArrayList<ActionSummaryCardDto>();
		listCardActionsOccurAndRestore = new ArrayList<ActionSummaryCardDto>();
		listCardActionsOccur = new ArrayList<ActionSummaryCardDto>();
		listCardActionsRestore = new ArrayList<ActionSummaryCardDto>();
		listCardActionsError = new ArrayList<ActionSummaryCardDto>();
		
		// Exception
		if (nodeTrigger == null)
			return "redirect:/trigger?err";
		
		for (NodeTriggerAction entity : nodeTrigger.getNodeTriggerActions())
		{
			switch (entity.getTriggerStatus())
			{
				case ALL:
					listCardActionsAll.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case OCCUR_AND_RESTORE:
					listCardActionsOccurAndRestore.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case OCCUR:
					listCardActionsOccur.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case RESTORE:
					listCardActionsRestore.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case ERROR:
					listCardActionsError.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
			}
		}
		
		// Process
		model.addAttribute("cardTriggerInfo", new TriggerInfoCardDto(nodeTrigger));
		model.addAttribute("listCardActionsAll", listCardActionsAll);
		model.addAttribute("listCardActionsOccurAndRestore", listCardActionsOccurAndRestore);
		model.addAttribute("listCardActionsOccur", listCardActionsOccur);
		model.addAttribute("listCardActionsRestore", listCardActionsRestore);
		model.addAttribute("listCardActionsError", listCardActionsError);
		
		// Return
		return G_BASE_PATH + "/TriggerInfo";
	}
	
	@GetMapping("/modify/{id}")
	public String dispTriggerModify(Model model, Authentication auth, @PathVariable("id") Long triggerId)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		NodeTrigger nodeTrigger;
		List<NodeAction> listNodeAction;
		List<ActionSummaryCardDto> listCardActionsAll;
		List<ActionSummaryCardDto> listCardActionsOccurAndRestore;
		List<ActionSummaryCardDto> listCardActionsOccur;
		List<ActionSummaryCardDto> listCardActionsRestore;
		List<ActionSummaryCardDto> listCardActionsError;
		
		// Init
		nodeTrigger = iotTriggerService.getTrigger(auth, triggerId);
		listNodeAction = iotActionService.getAllNodeActions(auth);
		listCardActionsAll = new ArrayList<ActionSummaryCardDto>();
		listCardActionsOccurAndRestore = new ArrayList<ActionSummaryCardDto>();
		listCardActionsOccur = new ArrayList<ActionSummaryCardDto>();
		listCardActionsRestore = new ArrayList<ActionSummaryCardDto>();
		listCardActionsError = new ArrayList<ActionSummaryCardDto>();
		
		// Exception
		if (nodeTrigger == null)
			return "redirect:/trigger?err";
		
		for (NodeTriggerAction entity : nodeTrigger.getNodeTriggerActions())
		{
			switch (entity.getTriggerStatus())
			{
				case ALL:
					listCardActionsAll.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case OCCUR_AND_RESTORE:
					listCardActionsOccurAndRestore.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case OCCUR:
					listCardActionsOccur.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case RESTORE:
					listCardActionsRestore.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
					
				case ERROR:
					listCardActionsError.add(new ActionSummaryCardDto(entity.getNodeAction()));
					break;
			}
			
			listNodeAction.remove(entity.getNodeAction());
		}
		
		// Process
		model.addAttribute("cardTriggerInfo", new TriggerInfoCardDto(nodeTrigger));
		model.addAttribute("listCardActionsAll", listCardActionsAll);
		model.addAttribute("listCardActionsOccurAndRestore", listCardActionsOccurAndRestore);
		model.addAttribute("listCardActionsOccur", listCardActionsOccur);
		model.addAttribute("listCardActionsRestore", listCardActionsRestore);
		model.addAttribute("listCardActionsError", listCardActionsError);
		model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction));
		
		// Return
		return G_BASE_PATH + "/TriggerModify";
	}
	
	@PostMapping("/create")
	public String procCreate(Authentication auth, IotTriggerDto iotTriggerDto)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
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
	
	@PostMapping("/modify/{id}")
	public String procModify(Authentication auth, @PathVariable("id") Long triggerId, IotTriggerDto iotTriggerDto)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		boolean isSuccess;
		
		// Process
		isSuccess = iotTriggerService.modifyTrigger(auth, triggerId, iotTriggerDto);

		// Return
		if (isSuccess)
			return "redirect:/trigger/" + triggerId;
		else
			return "redirect:/trigger/" + triggerId + "?err";
	}
	
	@PostMapping("/delete/{id}")
	public String procDelete(Authentication auth, @PathVariable("id") Long triggerId)
	{
		// Exception
		if (centerService.getSetting().getIotTrigger() != 1)
			return G_INDEX_REDIRECT_URL;
		
		// Field
		boolean isSuccess;
		
		// Process
		isSuccess = iotTriggerService.deleteTrigger(auth, triggerId);
		
		// Return
		if (isSuccess)
			return "redirect:/trigger";
		else
			return "redirect:/trigger?err";
	}
}
