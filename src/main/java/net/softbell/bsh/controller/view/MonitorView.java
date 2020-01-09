package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터 뷰 컨트롤러
 */
@Controller
@RequestMapping("/monitor")
public class MonitorView {
	// Global Field
	private final String G_BASE_PATH = "services/monitor";
	
	@GetMapping()
    public String dispIndex() {
        return G_BASE_PATH + "/index";
    }
}
