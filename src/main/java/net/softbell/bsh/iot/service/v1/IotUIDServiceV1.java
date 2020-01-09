package net.softbell.bsh.iot.service.v1;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotComponentV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1DTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT UID 서비스
 */
@Service
public class IotUIDServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotComponentV1 iotComponentV1;
	@Autowired
	private NodeRepo nodeRepo;


	@Transactional
	public boolean setNewNodeInfo(String uid, NodeInfoV1DTO nodeInfo)
	{
		// Field
		BaseV1DTO message;
		Node node;
		
		// Init
		node = nodeRepo.findByUid(uid);
		
		// Exception
		if (node != null)
			return false;
		
		// Process
		node = Node.builder().uid(nodeInfo.getUid())
							.controlMode(nodeInfo.getControlMode())
							.nodeName(nodeInfo.getNodeName())
							.alias(nodeInfo.getNodeName())
							.registerDate(new Date())
							.enableStatus(EnableStatusRule.ENABLE) // ############## TODO 개발중 인증패스
							.build();
		
		// DB - Save
		nodeRepo.save(node);

		// Message
		message = BaseV1DTO.builder().sender("SERVER")
									.target(uid)
									.cmd("INFO")
									.type("NEW")
									.obj("NODE")
									.value("SUCCESS")
									.build();
		iotComponentV1.sendDataUID(message); // Send
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "New Node Info Save (" + nodeInfo.getUid() + ")");
		
		// Return
		return true;
	}
	
	public boolean generateToken(String uid)
	{
		// Field
		BaseV1DTO message;
		Node node;
		String token;
		
		// Init
		node = nodeRepo.findByUid(uid);
		
		// Process
		if (node == null)
			message = BaseV1DTO.builder().sender("SERVER").target(uid)
										.cmd("GET")
										.type("INFO")
										.obj("NODE")
										.build();
		else
		{
			token = iotComponentV1.generateToken(uid);
			
			message = BaseV1DTO.builder().sender("SERVER").target(uid)
										.cmd("SET")
										.type("INFO")
										.obj("TOKEN")
										.value(token)
										.build();
		}
		
		// Send
		iotComponentV1.sendDataUID(message);
		
		// Return
		return true;
	}
}
