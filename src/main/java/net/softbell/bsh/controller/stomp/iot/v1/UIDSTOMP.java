package net.softbell.bsh.controller.stomp.iot.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.bshp.BaseV1DTO;
import net.softbell.bsh.service.IotService;

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP UID Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
public class UIDSTOMP
{
	// Global Field
	@Autowired
	private IotService iotService;
	
	
	@MessageMapping("/iot/v1/node/uid/{uid}/GET/INFO/TOKEN")
//	@SendTo("/iot/v1/node/uid/1")
//	@SendTo("/api/stomp/topic/iot/v1/node/uid/1") // 이건 동작
	@SendToUser("/iot/v1/node/uid/1")
//	@SendToUser("/api/stomp/topic/iot/v1/node/uid/1")
	public BaseV1DTO NodeHandlerRegister(@DestinationVariable("uid") String uid)
	{
		// Field
		BaseV1DTO message;
		
		// Init
		message = BaseV1DTO.builder().sender("SERVER")
									.target(uid)
									.cmd("SET")
									.type("INFO")
									.obj("TOKEN")
									.value("토큰값!!!") // TODO 토큰 생성해서 DB에 저장 후 반환해주는 기능 필요
									.build();
		
		System.out.println("와짐?");
		
		// Return
		return message;
	}
}
