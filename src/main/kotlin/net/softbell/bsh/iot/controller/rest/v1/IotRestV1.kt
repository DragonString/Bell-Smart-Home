package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot")
public class IotRestV1
{
	@GetMapping("/send")
	public String procSend()
	{
		// Field
		
		
		// Finish
		return "Success";
	}
}
