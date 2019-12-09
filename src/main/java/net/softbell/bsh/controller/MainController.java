package net.softbell.bsh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.BellsmarthomeApplication;
import net.softbell.bsh.db.model.NodeInfo;
import net.softbell.bsh.db.repository.NodeInfoRepo;
import net.softbell.bsh.libs.BellLog;
import net.softbell.bsh.model.card.CardDashboard;
import net.softbell.bsh.model.card.CardItem;
import net.softbell.bsh.model.navbar.NavBar;

@Controller
public class MainController {
	// Static Field
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	private void setNavBar(Model model)
	{
		List<Object> navbar = new ArrayList<Object>();
		
		List<NavBar> left_nav_item = new ArrayList<NavBar>();
		List<NavBar> nav_drop_profile = new ArrayList<NavBar>();
		List<NavBar> right_nav_item = new ArrayList<NavBar>();
		
		nav_drop_profile.add(new NavBar("Preference", "/preference", NavBar.NavType.LINK));
		nav_drop_profile.add(new NavBar("", "", NavBar.NavType.DIVIDER));
		nav_drop_profile.add(new NavBar("Logout", "/logout", NavBar.NavType.LINK));
		
		left_nav_item.add(new NavBar("Dash Board", "/dashboard", NavBar.NavType.LINK, true, null));
		left_nav_item.add(new NavBar("Monitor", "/monitor", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("Control", "/control", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("Setting", "/setting", NavBar.NavType.LINK));
		
		left_nav_item.add(new NavBar("LED ON", "/led_on", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("LED OFF", "/led_off", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("OFF", "/off", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("RED", "/red", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("GREEN", "/green", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("BLUE", "/blue", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("WHITE", "/white", NavBar.NavType.LINK));
		
		right_nav_item.add(new NavBar("Profile", "navDrop", NavBar.NavType.DROPDOWN, false, nav_drop_profile));
		right_nav_item.add(new NavBar("Admin", "/admin", NavBar.NavType.DISABLED));
		
		navbar.add(left_nav_item);
		navbar.add(right_nav_item);
		
		model.addAttribute("nav_brand", "BSH");
		model.addAttribute("navbar", navbar);
	}
	
	private void setDashBoard(Model model)
	{
		List<CardDashboard> cards = new ArrayList<CardDashboard>();
		
		List<CardItem> card_items = new ArrayList<CardItem>();
		
		card_items.add(new CardItem("Key1", "Value"));
		card_items.add(new CardItem("Key2", "Value", CardItem.ItemType.DANGER));
		card_items.add(new CardItem("Key3", "Value", CardItem.ItemType.WARNING));
		
		for (int i = 1; i < 160; i++)
			cards.add(new CardDashboard("Topic " + i, "Last update " + i + " mins ago", card_items));
		
		model.addAttribute("cards", cards);
	}
	
	@RequestMapping({"/", "/main"})
	public String main(Model model)
	{
//		logger.info(BellLog.getLogHead() + "update Conn");
		setNavBar(model);
		setDashBoard(model);
//		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}
	
	@Autowired
	NodeInfoRepo nir;
	
	@RequestMapping({"/save"})
	public String save(Model model)
	{
		nir.save(NodeInfo.builder().chipId(1).nodeName("builder").registerTime(new Date()).modeState((byte)2).build());
		
		logger.info(BellLog.getLogHead() + "update Conn");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		return "bs_layout";
	}
	
	@RequestMapping({"/load"})
	public String load(Model model)
	{
		// Log
		logger.info(BellLog.getLogHead() + "update Conn");
		
		// Field
		List<NodeInfo> niList = (List<NodeInfo>) nir.findAll();
		List<CardDashboard> cards = new ArrayList<CardDashboard>();
		List<CardItem> card_items = new ArrayList<CardItem>();
		
		// Init
		setNavBar(model);
		
		// Process
		for (NodeInfo value : niList)
			card_items.add(new CardItem(Long.toString(value.getNodeId()), value.getNodeName()));
		
		// Post-Process
		cards.add(new CardDashboard("Topic ", "Last update mins ago", card_items));
		
		// Finish
		model.addAttribute("cards", cards);
		
		// Log
		logger.info(BellLog.getLogHead() + "update Complate");
		
		return "bs_layout";
	}

	@RequestMapping({"/led_on"})
	public String led_on(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#1");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}

	@RequestMapping({"/led_off"})
	public String led_off(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#1#0");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}

	@RequestMapping({"/off"})
	public String off(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#0");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}
	
	@RequestMapping({"/red"})
	public String red(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#R");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}

	@RequestMapping({"/green"})
	public String green(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#G");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}

	@RequestMapping({"/blue"})
	public String blue(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#B");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}

	@RequestMapping({"/white"})
	public String white(Model model)
	{
		logger.info(BellLog.getLogHead() + "update Conn");
		BellsmarthomeApplication.nettyServer.broadcast("NODE#SET#VALUE#ITEM#2#W");
		setNavBar(model);
		setDashBoard(model);
		logger.info(BellLog.getLogHead() + "update Complate");
		
		
		return "bs_layout";
	}
	
	@RequestMapping("/bs_test")
    public String bs_test(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "test");
        model.addAttribute("mode", "앱모드");

        //return "bs_test";
        return "NavBar_Example";
    }
	
	@RequestMapping({"/broadcast"})
	public String broadcast(Model model)
	{
		BellsmarthomeApplication.nettyServer.broadcast("IoT Center Test MSG ");
		logger.info(BellLog.getLogHead() + "Broadcast Complete");
		
		return "hello";
	}
	
	@RequestMapping({"/user/test"})
	public String test(Model model)
	{
		logger.info(BellLog.getLogHead() + "user/test call!!");
		
		
		return "home";
	}
		
	@RequestMapping({"/temp"})
	public String getTest(Model model)
	{
		Test user = new Test("kkaok", "테스트", "web") ;
        model.addAttribute("user", user);
        return "test";
	}
	
	@Setter
	@Getter
	private class Test
	{
		private String userId;
	    private String userPwd;
	    private String name;
	    private String authType;
	    
	    public Test(String userId, String name, String authType) {
	        super();
	        this.userId = userId;
	        this.name = name;
	        this.authType = authType;
	    }
	    
	    @Override
	    public String toString() {
	        return "User [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", authType=" + authType + "]";
	    }
	}
}
