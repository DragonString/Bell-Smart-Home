package net.softbell.bsh.iot.component.v1;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 컴포넌트 v1
 */
@Slf4j
@Component
public class IotAuthCompV1
{
	// Global Field
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
	
	@Autowired
	private NodeRepo nodeRepo;
	
	
	public String getRandomToken()
	{
		// Field
		String token;
		byte[] randomBytes = new byte[24];

		// Generate
		secureRandom.nextBytes(randomBytes);
	    token = base64Encoder.encodeToString(randomBytes);
		
		// Return
		return token;
	}
	
	@Transactional
	public String generateToken(String uid)
	{
		// Field
		Node node;
		String token;
		
		// Init
		node = nodeRepo.findByUid(uid);
		
		// Exception
		if (node == null)
			return null;
	    
		// Generate
	    token = getRandomToken();
	    node.setToken(token);
		
		// DB - Save
		nodeRepo.save(node);
	    
	    // Log
	    log.info(BellLog.getLogHead() + "보안 토큰 생성 (" + uid + "->" + token + ")");
		
		// Return
		return token;
	}
	
	public boolean isTokenAvailable(String token)
	{
		if (nodeRepo.findByToken(token) == null)
			return false;
		
		return true;
	}
	
	/**
	 * 노드가 승인상태인지 검증
	 * @param node: 노드 엔티티
	 * @return true: 승인, false: 미승인 혹은 제한
	 */
	public boolean isApprovalNode(Node node)
	{
		// Field
		EnableStatusRule enableStatus = node.getEnableStatus();
		
		// Process
		if (enableStatus == EnableStatusRule.WAIT || enableStatus == EnableStatusRule.REJECT)
			return false;
		return true;
	}
}
