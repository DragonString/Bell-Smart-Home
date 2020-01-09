package net.softbell.bsh.iot.component.v1;

import java.security.SecureRandom;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 컴포넌트 v1
 */
@Component
public class IotAuthCompV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
	
	@Autowired
	private NodeRepo nodeRepo;
	
	
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
