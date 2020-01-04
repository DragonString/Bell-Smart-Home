package net.softbell.bsh.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.softbell.bsh.dto.card.CardDashboard;
import net.softbell.bsh.dto.card.CardItem;

@Controller
@RequestMapping("/")
public class IndexView {
	// Global Field
	@SuppressWarnings("unused")
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());

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
	
	@GetMapping()
	public String dispIndex(Model model)
	{
//		G_Logger.info(BellLog.getLogHead() + "update Conn");
		setDashBoard(model);
//		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "bs_layout";
	}
}
