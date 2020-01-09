package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.iot.component.v1.IotComponentV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot")
public class IotRestV1
{
	@Autowired
	private IotComponentV1 iotComponent;
	
	@GetMapping("/send")
	public String procSend()
	{
		// Field
		
		
		// Finish
		return "Success";
	}
}
