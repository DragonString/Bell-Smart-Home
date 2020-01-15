package net.softbell.bsh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotControlServiceV1;
import net.softbell.bsh.iot.service.v1.IotMonitorServiceV1;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 컨트롤 페이지 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/control")
public class ControlView
{
	// Global Field
	private final String G_BASE_PATH = "services/control";
    private final IotControlServiceV1 iotControlService;
    private final IotMonitorServiceV1 iotMonitorService;
	
	@GetMapping()
    public String dispIndex()
	{
        return G_BASE_PATH + "/index";
    }
}
