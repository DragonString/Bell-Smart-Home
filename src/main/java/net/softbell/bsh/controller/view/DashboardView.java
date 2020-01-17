package net.softbell.bsh.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.softbell.bsh.dto.card.CardDashboardDto;
import net.softbell.bsh.dto.card.CardItemDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 뷰 컨트롤러
 */
@Controller
@RequestMapping("/")
public class DashboardView
{
	// Global Field

	private void setDashBoard(Model model)
	{
		List<CardDashboardDto> cards = new ArrayList<CardDashboardDto>();
		
		List<CardItemDto> card_items = new ArrayList<CardItemDto>();
		
		card_items.add(new CardItemDto("Key1", "Value"));
		card_items.add(new CardItemDto("Key2", "Value", CardItemDto.ItemType.DANGER));
		card_items.add(new CardItemDto("Key3", "Value", CardItemDto.ItemType.WARNING));
		
		for (int i = 1; i < 160; i++)
			cards.add(new CardDashboardDto("Topic " + i, "Last update " + i + " mins ago", card_items));
		
		model.addAttribute("cards", cards);
	}
	
	@GetMapping()
	public String dispIndex(Model model)
	{
//		G_Logger.info(BellLog.getLogHead() + "update Conn");
		setDashBoard(model);
//		G_Logger.info(BellLog.getLogHead() + "update Complate");
		
		return "services/dashboard/Dashboard";
	}
}
