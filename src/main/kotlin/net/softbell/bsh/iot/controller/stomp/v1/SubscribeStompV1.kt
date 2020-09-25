package net.softbell.bsh.iot.controller.stomp.v1;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.iot.service.v1.IotSubscribeServiceV1;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Subscribe Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@AllArgsConstructor
@RestController
public class SubscribeStompV1
{
	// Global Field
	private final IotSubscribeServiceV1 iotSubscribeServiceV1;
	
	@SubscribeMapping("/iot/v1/node")
	public BaseV1Dto NodeTopicSubscribeHandler()
	{
		return iotSubscribeServiceV1.procTopicSubscribe();
	}
	
	@SubscribeMapping("/iot/v1/node/uid/{uid}")
	public BaseV1Dto NodeUIDSubscribeHandler(@DestinationVariable("uid") String uid)
	{
		return iotSubscribeServiceV1.procUIDSubscribe(uid);
	}
	
	@SubscribeMapping("/iot/v1/node/token/{token}")
	public BaseV1Dto NodeTokenSubscribeHandler(@DestinationVariable("token") String token)
	{
		return iotSubscribeServiceV1.procTokenSubscribe(token);
	}
}
