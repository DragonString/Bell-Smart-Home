package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.softbell.bsh.dto.test.APITestDTO;
import net.softbell.bsh.iot.component.v1.IotComponentV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/rest/v1/test")
public class TestREST
{
	@Autowired()
	private IotComponentV1 iotComponent;
	
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
	public String procSend(@RequestParam(value = "target", required = false, defaultValue = "TOKEN_VALUE")String strTarget,
			@RequestParam(value = "cmd", required = false, defaultValue = "GET")String strCmd,
			@RequestParam(value = "type", required = false, defaultValue = "INFO")String strType,
			@RequestParam(value = "obj", required = false, defaultValue = "ITEMS")String strObj)
	{
		// Field
		BaseV1DTO message = new BaseV1DTO("SERVER", strTarget, strCmd, strType, strObj);
		
		// Process
		iotComponent.sendDataToken(message);
		
		// Finish
		return "Success";
	}
	
	@GetMapping("/sendSet")
	public String procSendSet(@RequestParam(value = "target", required = false, defaultValue = "TOKEN_VALUE")String strTarget,
			@RequestParam(value = "id", required = false, defaultValue = "2")int intId,
			@RequestParam(value = "value", required = false, defaultValue = "1")int intValue)
	{
		// Field
		BaseV1DTO message = new BaseV1DTO("SERVER", strTarget, "SET", "VALUE", "ITEM");
		Test2 test = new Test2(intId, intValue);
		message.setValue(test);
		
		// Process
		iotComponent.sendDataToken(message);
		
		// Finish
		return "Success";
	}
	
	@Data
	@AllArgsConstructor
	class Test2
	{
		private int pinId;
		private int pinStatus;
	}
}
