package net.softbell.bsh.controller.view.advance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.service.v1.IotTriggerServiceV1;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/trigger")
public class TriggerView
{
	// Global Field
	private final String G_BASE_PATH = "services/advance";
	private final ViewDtoConverterService viewDtoConverterService;
	private final IotTriggerServiceV1 iotTriggerService;
	
	@GetMapping()
    public String dispIndex(Model model)
	{
        return G_BASE_PATH + "/Trigger";
    }
}
