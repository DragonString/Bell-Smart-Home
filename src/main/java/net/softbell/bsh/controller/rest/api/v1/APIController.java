package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.test.APITestDTO;

@RestController
@RequestMapping("/api/rest/v1/")
public class APIController
{
	@GetMapping("test")
	public APITestDTO procTest(@RequestParam(value = "strName", required = false, defaultValue = "이름")String strName,
			@RequestParam(value = "intAge", required = false, defaultValue = "24")Integer intAge)
	{
		// Field
		APITestDTO apiTest = new APITestDTO(strName, intAge);
		
		// Finish
		return apiTest;
	}
}
