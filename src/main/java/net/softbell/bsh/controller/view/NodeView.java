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
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/node")
public class NodeView
{
	// Global Field
	private final String G_BASE_PATH = "services/advance";
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
		model.addAttribute("listNode", pageNode);
		
		// Return
        return G_BASE_PATH + "/NodeList";
    }
	
	@GetMapping("/{id}")
    public String dispNode(Model model, @PathVariable("id")int intNodeId)
	{
		// Field
		Node node;
		
		// Init
		node = iotNodeService.getNode(intNodeId);
		
		// Process
		model.addAttribute("nodeInfo", node);
		
		// Return
        return G_BASE_PATH + "/NodeInfo";
    }
	
	@GetMapping("/item/{id}")
    public String dispNodeItemHistory(Model model, @PathVariable("id")int intNodeItemId)
	{
		// Field
		List<NodeItemHistory> pageNodeItemHistory;
		NodeItem nodeItem;
		
		// Init
		pageNodeItemHistory = iotNodeService.getNodeItemHistory(intNodeItemId);
		nodeItem = iotNodeService.getNodeItem(intNodeItemId);
		
		// Process
		model.addAttribute("listNodeItemHistory", pageNodeItemHistory);
		model.addAttribute("nodeItem", nodeItem);
		
		// Return
        return G_BASE_PATH + "/nodeItemInfo";
    }
}
