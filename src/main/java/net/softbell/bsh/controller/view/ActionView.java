package net.softbell.bsh.controller.view;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.NodeItem;
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
    public String dispIndex()
	{
        return G_BASE_PATH + "/Action";
    }
	
	@GetMapping("/create")
    public String dispAdd(Model model, Authentication auth)
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
}
