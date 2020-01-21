package net.softbell.bsh.controller.view;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/monitor")
public class MonitorView
{
	// Global Field
	private final String G_BASE_PATH = "services/general";
	private final ViewDtoConverterService viewDtoConverterService;
	private final IotNodeServiceV1 iotNodeService;
	
	@GetMapping()
    public String dispIndex(Model model,
					@RequestParam(value = "page", required = false, defaultValue = "1")int intPage,
					@RequestParam(value = "count", required = false, defaultValue = "100")int intCount)
	{
		// Field
		Page<Node> pageNode;
		
		// Init
		pageNode = iotNodeService.getAllNodes(intPage, intCount);
		
		// Process
		model.addAttribute("listCards", viewDtoConverterService.convMonitorSummaryCards(pageNode.getContent()));
		
		// Return
        return G_BASE_PATH + "/Monitor";
    }
}
