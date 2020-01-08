package net.softbell.bsh.iot.controller.stomp.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.iot.service.v1.IotSubscribeServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Subscribe Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class SubscribeStompV1
{
	// Global Field
	@Autowired
	private IotSubscribeServiceV1 iotSubscribeServiceV1;
	
	
	@SubscribeMapping("/iot/v1/node")
	public BaseV1DTO NodeTopicSubscribeHandler()
	{
		return iotSubscribeServiceV1.procTopicSubscribe();
	}
	
	@SubscribeMapping("/iot/v1/node/uid/{uid}")
	public BaseV1DTO NodeUIDSubscribeHandler(@DestinationVariable("uid") String uid)
	{
		return iotSubscribeServiceV1.procUIDSubscribe(uid);
	}
	
	@SubscribeMapping("/iot/v1/node/token/{token}")
	public BaseV1DTO NodeTokenSubscribeHandler(@DestinationVariable("token") String token)
	{
		return iotSubscribeServiceV1.procTokenSubscribe(token);
	}
}
