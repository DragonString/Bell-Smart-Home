package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/test")
public class IotTestV1
{
	@Autowired
	private IotChannelCompV1 iotComponent;
	
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
