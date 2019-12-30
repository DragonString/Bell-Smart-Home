package net.softbell.bsh.controller.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.softbell.bsh.BellsmarthomeApplication;
import net.softbell.bsh.domain.entity.NodeInfo;
import net.softbell.bsh.domain.repository.NodeInfoRepo;
import net.softbell.bsh.dto.card.CardDashboard;
import net.softbell.bsh.dto.card.CardItem;
import net.softbell.bsh.libs.BellLog;

@Controller
@RequestMapping("/iot/")
public class IotController {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	NodeInfoRepo nir;

	@GetMapping("broadcast")
	public String procBroadcast(Model model)
	{
		BellsmarthomeApplication.nettyServer.broadcast("IoT Center Test MSG ");
		G_Logger.info(BellLog.getLogHead() + "Broadcast Complete");
		
		return "hello";
	}
	
	@GetMapping("save")
	public String procSave()
	{
		nir.save(NodeInfo.builder().chipId(1).nodeName("builder").registerTime(new Date()).modeState((byte)2).build());
		
		G_Logger.info(BellLog.getLogHead() + "update Conn");
		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "redirect:/";
	}
	
	@GetMapping("load")
	public String procLoad(Model model)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "update Conn");
		
		// Field
		List<NodeInfo> niList = (List<NodeInfo>) nir.findAll();
		List<CardDashboard> cards = new ArrayList<CardDashboard>();
		List<CardItem> card_items = new ArrayList<CardItem>();
		
		// Init
		
		// Process
		for (NodeInfo value : niList)
			card_items.add(new CardItem(Long.toString(value.getNodeId()), value.getNodeName()));
		
		// Post-Process
		cards.add(new CardDashboard("Topic ", "Last update mins ago", card_items));
		
		// Finish
		model.addAttribute("cards", cards);
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "bs_layout";
	}

	@GetMapping("led")
	public String procLEDs(@RequestParam("mode")String strMode,
			@RequestParam("value")String strValue)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "LED Update Process Start");
		
		// Field
		boolean isSuccess = false;
		
		// Process
		if (strMode.equalsIgnoreCase("circuit"))
			isSuccess = procLED_Circuit(strValue);
		else if (strMode.equalsIgnoreCase("bar"))
			isSuccess = procLED_Bar(strValue);
		
		G_Logger.info(BellLog.getLogHead() + "LED Update Process Finish");
		
		// Finish
		if (isSuccess)
			return "redirect:/";
		else
			return "redirect:/error";
	}
	
	private boolean procLED_Circuit(String strValue)
	{
		if (strValue.equalsIgnoreCase("on"))
			BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#1");
		else if (strValue.equalsIgnoreCase("off"))
			BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#0");
		else
			return false;
		
		return true;
	}
	
	private boolean procLED_Bar(String strValue)
	{
		// Field
		String strMode;
		
		// Init
		if (strValue.equalsIgnoreCase("off"))
			strMode = "0";
		else if (strValue.equalsIgnoreCase("red"))
			strMode = "R";
		else if (strValue.equalsIgnoreCase("green"))
			strMode = "G";
		else if (strValue.equalsIgnoreCase("blue"))
			strMode = "B";
		else if (strValue.equalsIgnoreCase("white"))
			strMode = "W";
		else
			return false;
		
		// Process
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#" + strMode);
		
		// Finish
		return true;
	}
}
