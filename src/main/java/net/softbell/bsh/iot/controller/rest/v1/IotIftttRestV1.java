package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT IFTTT REST API 컨트롤러 V1
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/ifttt")
public class IotIftttRestV1
{
    private final ResponseService responseService;
    private final IotActionServiceV1 iotActionService;
	
	@PostMapping("/action/{id}")
	public ResultDto execNodeAction(@PathVariable("id")long actionId/*,
									@RequestParam("id")String id,
									@RequestParam("password")String password*/)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotActionService.execAction(actionId);
		
		// Log
		log.info("IFTTT Action 요청됨 (" + actionId + ")");
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
}
