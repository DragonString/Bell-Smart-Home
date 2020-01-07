package net.softbell.bsh.controller.stomp.iot.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.bshp.ItemInfoV1DTO;
import net.softbell.bsh.dto.bshp.ItemValueV1DTO;
import net.softbell.bsh.dto.bshp.NodeInfoV1DTO;
import net.softbell.bsh.service.IotService;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Token Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class TokenSTOMP
{
	// Global Field
	@Autowired
	private IotService iotService;
	
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/NODE")
	public void NodeHandlerSetInfoNode(@DestinationVariable("token") String token, NodeInfoV1DTO nodeInfo)
	{
		System.out.println(token + " 핸들러 접근 1");
//		iotService.procMessage(message);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEMS")
	public void NodeHandlerSetInfoItems(@DestinationVariable("token") String token, List<ItemInfoV1DTO> listItemInfo)
	{
		System.out.println(token + " 핸들러 접근 2");
		for (ItemInfoV1DTO value : listItemInfo)
			System.out.println(value.getPinName());
//		iotService.procMessage(message);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEM")
	public void NodeHandlerSetInfoItem(@DestinationVariable("token") String token, ItemInfoV1DTO itemInfo)
	{
		System.out.println(token + " 핸들러 접근 3");
		System.out.println(itemInfo.getPinName());
//		iotService.procMessage(message);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/VALUE/ITEM")
	public void NodeHandlerSetValueItem(@DestinationVariable("token") String token, ItemValueV1DTO itemValue)
	{
		System.out.println(token + " 핸들러 접근 4");
//		iotService.procMessage(message);
	}
}
