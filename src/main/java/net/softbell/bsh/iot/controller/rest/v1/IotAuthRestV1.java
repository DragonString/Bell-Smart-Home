package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot/auth")
public class IotAuthRestV1
{
	private final IotAuthCompV1 iotAuthComp;
	
	@GetMapping("/token/check")
	public ResultDto checkTokenAvailable(@RequestParam("token")String token)
	{
		// Field
		ResultDto message;
		boolean isAvailable;
		
		// Check
		isAvailable = iotAuthComp.isTokenAvailable(token);
		
		// Message
		if (isAvailable)
			message = ResultDto.builder().message("valid").build();
		else
			message = ResultDto.builder().message("invalid").build();
		
		// Return
		return message;
	}
}
