package net.softbell.bsh.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.dto.card.CardDashboard;
import net.softbell.bsh.dto.card.CardItem;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.iot.service.v1.IotTokenServiceV1;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 뷰 컨트롤러
 */
@Controller
@RequestMapping("/iot/")
public class IotView {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NodeRepo nodeRepo;
	@Autowired
	IotTokenServiceV1 iotService;

	@Deprecated
	@GetMapping("save")
	public String procSave()
	{
		//nodeRepo.save(Node.builder().uid("1").nodeName("builder").registerDate(new Date()).modeState((byte)2).build());
		
		G_Logger.info(BellLog.getLogHead() + "update Conn");
		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "redirect:/";
	}
	
	@Deprecated
	@GetMapping("load")
	public String procLoad(Model model)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "update Conn");
		
		// Field
		List<Node> niList = (List<Node>) nodeRepo.findAll();
		List<CardDashboard> cards = new ArrayList<CardDashboard>();
		List<CardItem> card_items = new ArrayList<CardItem>();
		
		// Init
		
		// Process
		for (Node value : niList)
			card_items.add(new CardItem(Long.toString(value.getNodeId()), value.getNodeName()));
		
		// Post-Process
		cards.add(new CardDashboard("Topic ", "Last update mins ago", card_items));
		
		// Finish
		model.addAttribute("cards", cards);
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "bs_layout";
	}

	@Deprecated
	@GetMapping("led")
	public String procLEDs(@RequestParam("mode")String strMode,
			@RequestParam("value")String strValue)
	{
		// Field
		BaseV1DTO msg;
		
		// Init
		msg = BaseV1DTO.builder().sender("SERVER").target("1").cmd("SET").type("ITEM").obj("1").value("ON").build();
		
		// Process
//		iotService.sendMessage(msg);
		
		// Finish
		return "redirect:/";
	}
}
