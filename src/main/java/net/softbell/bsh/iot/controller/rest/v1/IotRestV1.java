package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot")
public class IotRestV1
{
	private final IotAuthCompV1 iotAuthComp;
	
	@GetMapping("/send")
	public String procSend()
	{
		// Field
		
		
		// Finish
		return "Success";
	}
}
