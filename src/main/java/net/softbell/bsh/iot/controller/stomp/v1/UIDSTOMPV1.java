package net.softbell.bsh.iot.controller.stomp.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.iot.service.v1.IotUIDServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP UID Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class UIDSTOMPV1
{
	// Global Field
	@Autowired
	private IotUIDServiceV1 iotUIDService;
	
	
	@MessageMapping("/iot/v1/node/uid/{uid}/GET/INFO/TOKEN")
	public BaseV1DTO NodeHandlerRegister(@DestinationVariable("uid") String uid)
	{
		return iotUIDService.generateToken(uid);
	}
}
