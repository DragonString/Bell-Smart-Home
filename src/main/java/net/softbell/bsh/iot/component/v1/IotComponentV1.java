package net.softbell.bsh.iot.component.v1;

import java.security.SecureRandom;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 기반 컴포넌트 v1
 */
@Component
public class IotComponentV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	private final String G_UID_URL = "/api/stomp/queue/iot/v1/node/uid/";
	private final String G_TOKEN_URL = "/api/stomp/queue/iot/v1/node/token/";
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
	
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private NodeRepo nodeRepo;
	
	
	// ## IOT COMMUNICATION CHANNEL START ##
	public void sendDataUID(BaseV1DTO data)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "IoT 메시지 전송 (UID: " + data.getTarget() + ")");
		
		// Process
		template.convertAndSend(G_UID_URL + data.getTarget(), data);
	}
	
	public void sendDataToken(BaseV1DTO data)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + "IoT 메시지 전송 (Token: " + data.getTarget() + ")");
		
		// Process
		template.convertAndSend(G_TOKEN_URL + data.getTarget(), data);
	}
	// ## IOT COMMUNICATION CHANNEL END ##
	
	@Transactional
	public String generateToken(String uid)
	{
		// Field
		Node node;
		String token;
		byte[] randomBytes = new byte[24];
		
		// Init
		node = nodeRepo.findByUid(uid);
		
		// Exception
		if (node == null)
			return null;
	    
		// Generate
		secureRandom.nextBytes(randomBytes);
	    token = base64Encoder.encodeToString(randomBytes);
	    node.setToken(token);
		
		// DB - Save
		nodeRepo.save(node);
	    
	    // Log
	    G_Logger.info(BellLog.getLogHead() + "보안 토큰 생성 (" + uid + "->" + token + ")");
		
		// Return
		return token;
	}
	
	public boolean isApprovalNode(Node node)
	{
		if (node.getEnableStatus() < 1)
			return false;
		return true;
	}
}
