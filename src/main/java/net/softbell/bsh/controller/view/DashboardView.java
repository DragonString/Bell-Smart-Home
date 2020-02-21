package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.service.DashboardService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 뷰 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboardView
{
	// Global Field
	private final DashboardService dashboardService;
	
	@GetMapping()
	public String dispIndex(Model model)
	{
		// Field
		
		// Init
		
		// Process
		model.addAttribute("listCardHumidityWarns", dashboardService.getHumidityWarn());
		model.addAttribute("listCardTemperatureWarns", dashboardService.getTemperatureWarn());
		
		// Return
		return "services/dashboard/Dashboard";
	}
}
