package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 액션 REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot/action")
public class IotActionRestV1
{
    private final ResponseService responseService;
    private final IotNodeServiceV1 iotNodeService;
	
	@PostMapping("/exec/{id}")
	public ResultDto setNodeItemValue(@PathVariable("id")long actionId)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = false; //iotNodeService.setItemValue(id, value); // TODO
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
}
