package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 뷰 컨트롤러
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardView {
	// Global Field
	private final String G_BASE_PATH = "services/dashboard";
	
	@GetMapping()
    public String dispIndex() {
        return G_BASE_PATH + "/index";
    }
}
