package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 뷰 컨트롤러
 */
@Controller
@RequestMapping("/trigger")
public class TriggerView
{
	// Global Field
	private final String G_BASE_PATH = "services/advance";
	
	@GetMapping()
    public String dispIndex()
	{
        return G_BASE_PATH + "/Trigger";
    }
}
