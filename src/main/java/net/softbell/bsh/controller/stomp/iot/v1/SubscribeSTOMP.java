package net.softbell.bsh.controller.stomp.iot.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.bshp.BaseV1DTO;
import net.softbell.bsh.service.IotService;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Subscribe Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class SubscribeSTOMP
{
	// Global Field
	@Autowired
	private IotService iotService;
	
	
	@SubscribeMapping("/iot/v1/node")
//	@SendToUser("/iot/v1/node")
	public BaseV1DTO NodeTopicSubscribeHandler()
	{
		
		// Field
		BaseV1DTO msgInfoReturn;
		
		// Init
		msgInfoReturn = BaseV1DTO.builder().sender("SERVER")
				.target("test")
				.cmd("SET")
				.type("INFO")
				.obj("TOKEN")
				.value("토큰값!!!") // TODO 토큰 생성해서 DB에 저장 후 반환해주는 기능 필요
				.build();
		
		// TEST LOG ############
		System.out.println("Sub Node");
		
		// Return
		return msgInfoReturn;
	}
	
	@SubscribeMapping("/iot/v1/node/uid/{uid}")
//	@SendToUser("/iot/v1/node")
	public BaseV1DTO NodeUIDSubscribeHandler(@DestinationVariable("uid") String uid)
	{
		// Field
		List<BaseV1DTO> listMsg;
		BaseV1DTO msgInfoReturn;
		
		// Init
		listMsg = new ArrayList<BaseV1DTO>();
//		listMsg.add(BaseV1DTO.builder().sender("SERVER").target(uid).cmd("GET").type("INFO").obj("NODE").build());
//		msgInfoReturn = BaseV1DTO.builder().sender("SERVER").target(uid).cmd("INFO").type("CONNECTION").obj("UID").value("SUCCESS").build();
		msgInfoReturn = BaseV1DTO.builder().sender("SERVER")
											.target(uid)
											.cmd("SET")
											.type("INFO")
											.obj("TOKEN")
											.value("TOKEN_VALUE")
											.build();
		
		
		// Process
//		for (BaseV1DTO message : listMsg)
//			iotService.sendMessage(message);
		
		// TEST LOG ############
		System.out.println("Sub UID: " + uid);
		
		// Return
		return msgInfoReturn;
	}
	
	@SubscribeMapping("/iot/v1/node/token/{token}")
	public BaseV1DTO NodeTokenSubscribeHandler(@DestinationVariable("token") String token)
	{
		// Field
		List<BaseV1DTO> listMsg;
		BaseV1DTO msgInfoReturn;
		
		// Init
		listMsg = new ArrayList<BaseV1DTO>();
		listMsg.add(BaseV1DTO.builder().sender("SERVER").target(token).cmd("GET").type("INFO").obj("NODE").build());
		listMsg.add(BaseV1DTO.builder().sender("SERVER").target(token).cmd("GET").type("INFO").obj("ITEMS").build());
		msgInfoReturn = BaseV1DTO.builder().sender("SERVER").target(token).cmd("INFO").type("CONNECTION").obj("TOKEN").value("SUCCESS").build();
		
		// Process
		for (BaseV1DTO message : listMsg)
			iotService.sendMessage(message);

		// TEST LOG ############
		System.out.println("Sub Token: " + token);
		
		// Return
		return msgInfoReturn;
	}
}
