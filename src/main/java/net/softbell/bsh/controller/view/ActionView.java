package net.softbell.bsh.controller.view;

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
import net.softbell.bsh.domain.entity.NodeActionItem;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.dto.request.IotActionDto;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/action")
public class ActionView
{
	// Global Field
	private final String G_BASE_PATH = "services/general";
	private final IotActionServiceV1 iotActionService;
	
	@GetMapping()
    public String dispIndex(Model model, Authentication auth)
	{
		// Field
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeAction = iotActionService.getAllNodeActions(auth);
		
		// Process
		model.addAttribute("listNodeAction", listNodeAction);
		
		// Return
        return G_BASE_PATH + "/Action";
    }
	
	@GetMapping("/{id}")
    public String dispAction(Model model, Authentication auth, @PathVariable("id") long actionId)
	{
		// Field
		NodeAction nodeAction;
		List<NodeItem> listNodeItem;
		
		// Init
		nodeAction = iotActionService.getNodeAction(auth, actionId);
		listNodeItem = iotActionService.getAvailableNodeItem(auth);
		
		for (NodeActionItem actionItem : nodeAction.getNodeActionItems())
			listNodeItem.remove(actionItem.getNodeItem());
		
		// Process
		model.addAttribute("actionInfo", nodeAction);
		model.addAttribute("listNodeItemAct", nodeAction.getNodeActionItems());
		model.addAttribute("listNodeItem", listNodeItem);
		
		// Return
        return G_BASE_PATH + "/ActionModify";
    }
	
	@GetMapping("/create")
    public String dispCreate(Model model, Authentication auth)
	{
		// Field
		List<NodeItem> listNodeItem;
		
		// Init
		listNodeItem = iotActionService.getAvailableNodeItem(auth);
		
		// Process
		model.addAttribute("listNodeItem", listNodeItem);
		
		// Return
        return G_BASE_PATH + "/ActionCreate";
    }
	
	@PostMapping("/create")
    public String procCreate(Model model, Authentication auth,
    						IotActionDto iotActionDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotActionService.createAction(auth, iotActionDto);
		
		// Return
		if (isSuccess)
			return "redirect:/action";
		else
			return "redirect:/action?error";
    }
	
	@PostMapping("/modify/{id}")
    public String procModify(Model model, Authentication auth,
    						@PathVariable("id") long actionId,
    						IotActionDto iotActionDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotActionService.modifyAction(auth, actionId, iotActionDto);
		
		// Return
		if (isSuccess)
			return "redirect:/action/";
		else
			return "redirect:/action/modify/" + actionId + "?error";
    }
	
	@PostMapping("/delete/{id}")
    public String procDelete(Model model, Authentication auth, @PathVariable("id") long actionId)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotActionService.deleteAction(auth, actionId);
		
		// Return
		if (isSuccess)
			return "redirect:/action";
		else
			return "redirect:/action/modify/" + actionId + "?error";
    }
}
