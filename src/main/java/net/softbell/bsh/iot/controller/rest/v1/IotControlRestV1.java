package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotControlServiceV1;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 제어 REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot/control")
public class IotControlRestV1
{
    private final ResponseService responseService;
    private final IotControlServiceV1 iotControlService;
	
	@PostMapping("/item/set/{id}")
	public ResultDto checkTokenAvailable(@PathVariable("id")long id, @RequestParam("value")short value)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotControlService.setItemValue(id, value);
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
}
