package net.softbell.bsh.iot.controller.stomp.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto;
import net.softbell.bsh.iot.service.v1.IotUIDServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP UID Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class UidStompV1
{
	// Global Field
	@Autowired
	private IotUIDServiceV1 iotUIDServiceV1;
	
	
	@MessageMapping("/iot/v1/node/uid/{uid}/SET/INFO/NODE")
	public void NodeHandlerNewNode(@DestinationVariable("uid") String uid, NodeInfoV1Dto nodeInfo)
	{
		iotUIDServiceV1.setNewNodeInfo(uid, nodeInfo);
	}
	
	@MessageMapping("/iot/v1/node/uid/{uid}/GET/INFO/TOKEN")
	public void NodeHandlerRegister(@DestinationVariable("uid") String uid)
	{
		iotUIDServiceV1.generateToken(uid);
	}
}
