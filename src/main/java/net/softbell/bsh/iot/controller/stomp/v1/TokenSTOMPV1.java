package net.softbell.bsh.iot.controller.stomp.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1DTO;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1DTO;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1DTO;
import net.softbell.bsh.iot.service.v1.IotTokenServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Token Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class TokenSTOMPV1
{
	// Global Field
	@Autowired
	private IotTokenServiceV1 iotTokenServiceV1;
	
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/NODE")
	public void NodeHandlerSetInfoNode(@DestinationVariable("token") String token, NodeInfoV1DTO nodeInfo)
	{
		iotTokenServiceV1.setNodeInfo(token, nodeInfo);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEMS")
	public void NodeHandlerSetInfoItems(@DestinationVariable("token") String token, List<ItemInfoV1DTO> listItemInfo)
	{
		for (ItemInfoV1DTO itemInfo : listItemInfo)
			iotTokenServiceV1.setItemInfo(token, itemInfo);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEM")
	public void NodeHandlerSetInfoItem(@DestinationVariable("token") String token, ItemInfoV1DTO itemInfo)
	{
		iotTokenServiceV1.setItemInfo(token, itemInfo);
	}
	
	@MessageMapping("/iot/v1/node/token/{token}/SET/VALUE/ITEM")
	public void NodeHandlerSetValueItem(@DestinationVariable("token") String token, ItemValueV1DTO itemValue)
	{
		iotTokenServiceV1.setItemValue(token, itemValue);
	}
}
