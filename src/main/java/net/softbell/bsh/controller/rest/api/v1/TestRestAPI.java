package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.iot.BSHPv1DTO;
import net.softbell.bsh.dto.test.APITestDTO;
import net.softbell.bsh.service.IotService;

@RestController
@RequestMapping("/api/rest/v1/test")
public class TestRestAPI
{
	@Autowired
	private IotService iotService;
	
	@GetMapping("/")
	public APITestDTO procTest(@RequestParam(value = "strName", required = false, defaultValue = "이름")String strName,
			@RequestParam(value = "intAge", required = false, defaultValue = "24")Integer intAge)
	{
		// Field
		APITestDTO apiTest = new APITestDTO(strName, intAge);
		
		// Finish
		return apiTest;
	}
	
	@GetMapping("/send")
	public String procSend(@RequestParam(value = "chipid", required = false, defaultValue = "1")String strChipid)
	{
		// Field
		BSHPv1DTO message = new BSHPv1DTO("SERVER", "1", "GET", "INFO", "ITEMS");
		
		// Process
		iotService.sendMessage(message);
		
		// Finish
		return "Success";
	}
}
