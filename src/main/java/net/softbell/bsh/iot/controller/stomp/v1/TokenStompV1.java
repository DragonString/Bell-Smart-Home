package net.softbell.bsh.iot.controller.stomp.v1;

import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto;
import net.softbell.bsh.iot.service.v1.IotTokenServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Token Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@AllArgsConstructor
@RestController
public class TokenStompV1
{
	// Global Field
	private final IotTokenServiceV1 iotTokenServiceV1;
	
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/NODE")
	public void NodeHandlerSetInfoNode(@DestinationVariable("token") String token, NodeInfoV1Dto nodeInfo)
	{
		iotTokenServiceV1.setNodeInfo(token, nodeInfo);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEMS")
	public void NodeHandlerSetInfoItems(@DestinationVariable("token") String token, List<ItemInfoV1Dto> listItemInfo)
	{
		for (ItemInfoV1Dto itemInfo : listItemInfo)
		{
			iotTokenServiceV1.setItemInfo(token, itemInfo);
			iotTokenServiceV1.reqItemValue(token, itemInfo.getPinId());
		}
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEM")
	public void NodeHandlerSetInfoItem(@DestinationVariable("token") String token, ItemInfoV1Dto itemInfo)
	{
		iotTokenServiceV1.setItemInfo(token, itemInfo);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/VALUE/ITEM")
	public void NodeHandlerSetValueItem(@DestinationVariable("token") String token, ItemValueV1Dto itemValue)
	{
		iotTokenServiceV1.setItemValue(token, itemValue);
	}
}
