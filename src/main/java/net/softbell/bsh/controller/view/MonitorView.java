package net.softbell.bsh.controller.view;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.iot.service.v1.IotMonitorServiceV1;

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
	private final String G_BASE_PATH = "services/monitor";
	private final IotMonitorServiceV1 iotMonitorService;
	
	@GetMapping()
    public String dispIndex(Model model,
    						@RequestParam(value = "page", required = false, defaultValue = "1")int intPage,
    						@RequestParam(value = "count", required = false, defaultValue = "10")int intCount)
	{
		// Field
		Page<Node> pageNode;
		
		// Init
		pageNode = iotMonitorService.getAllNodes(intPage, intCount);
		
		// Process
		model.addAttribute("listNode", pageNode);
		
		// Return
        return G_BASE_PATH + "/index";
    }
	
	@GetMapping("/{id}")
    public String dispNode(Model model, @PathVariable("id")int intNodeId)
	{
		// Field
		Node node;
		
		// Init
		node = iotMonitorService.getNode(intNodeId);
		
		// Process
		model.addAttribute("nodeInfo", node);
		
		// Return
        return G_BASE_PATH + "/nodeInfo";
    }
	
	@GetMapping("/item/{id}")
    public String dispNodeItemHistory(Model model, @PathVariable("id")int intNodeItemId)
	{
		// Field
		List<NodeItemHistory> pageNodeItemHistory;
		
		// Init
		pageNodeItemHistory = iotMonitorService.getNodeItemHistory(intNodeItemId);
		
		// Process
		model.addAttribute("listNodeItemHistory", pageNodeItemHistory);
		
		// Return
        return G_BASE_PATH + "/nodeItemInfo";
    }
}
