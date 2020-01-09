package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.rest.ResultDTO;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/auth")
public class IotAuthRestV1
{
	@Autowired
	private IotAuthCompV1 iotAuthComp;
	
	@GetMapping("/token/check")
	public ResultDTO checkTokenAvailable(@RequestParam("token")String token)
	{
		// Field
		ResultDTO message;
		boolean isAvailable;
		
		// Check
		isAvailable = iotAuthComp.isTokenAvailable(token);
		
		// Message
		if (isAvailable)
			message = ResultDTO.builder().message("valid").build();
		else
			message = ResultDTO.builder().message("invalid").build();
		
		// Return
		return message;
	}
}
