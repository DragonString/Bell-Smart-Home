package net.softbell.bsh.iot.component.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 서비스 기반 컴포넌트 v1
 */
@Component
public class IotComponentV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	private final String G_UID_URL = "/api/stomp/queue/iot/v1/node/uid/";
	private final String G_TOKEN_URL = "/api/stomp/queue/iot/v1/node/token/";
	
	@Autowired
	private SimpMessagingTemplate template;
	
	
	// ## IOT COMMUNICATION CHANNEL START ##
	public void sendDataUID(BaseV1DTO data)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "IoT 메시지 전송 (" + data.getTarget() + ")");
		
		// Process
		template.convertAndSend(G_UID_URL + data.getTarget(), data);
	}
	
	public void sendDataToken(BaseV1DTO data)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "IoT 메시지 전송 (" + data.getTarget() + ")");
		
		// Process
		template.convertAndSend(G_TOKEN_URL + data.getTarget(), data);
	}
	// ## IOT COMMUNICATION CHANNEL END ##
	
	public String generateToken(String uid)
	{
		// Field
		String token;
		
		// Generate
		token = "토큰 값 생성 기능!!"; // TODO
		
		// DB - Save
		// TODO 
		
		// Return
		return token;
	}
}
