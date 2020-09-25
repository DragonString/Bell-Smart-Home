package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotReservServiceV1;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 관련 REST API 컨트롤러 V1
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/reserv")
public class ReservRestV1
{
	// Global Field
	private final ResponseService responseService;
    private final IotReservServiceV1 iotReservService;
	
	@PostMapping("/status/{id}")
	public ResultDto setTriggerStatus(Authentication auth, @PathVariable("id")long id, @RequestParam("status")boolean status)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotReservService.setTriggerEnableStatus(auth, id, status);
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
}

