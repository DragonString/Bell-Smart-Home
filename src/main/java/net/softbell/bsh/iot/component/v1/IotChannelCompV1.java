package net.softbell.bsh.iot.component.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 채널 컴포넌트 v1
 */
@Component
public class IotChannelCompV1
{
	// Global Field
	private final String G_UID_URL = "/api/stomp/queue/iot/v1/node/uid/";
	private final String G_TOKEN_URL = "/api/stomp/queue/iot/v1/node/token/";
	
	@Autowired
	private SimpMessagingTemplate template;
	
	
	public void sendDataUID(BaseV1Dto data)
	{
		// Process
		template.convertAndSend(G_UID_URL + data.getTarget(), data);
	}
	
	public void sendDataToken(BaseV1Dto data)
	{
		// Process
		template.convertAndSend(G_TOKEN_URL + data.getTarget(), data);
	}
}
