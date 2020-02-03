package net.softbell.bsh.iot.service.v1;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT UID 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotUIDServiceV1
{
	// Global Field
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	private final NodeRepo nodeRepo;


	@Transactional
	public boolean setNewNodeInfo(String uid, NodeInfoV1Dto nodeInfo)
	{
		// Field
		BaseV1Dto message;
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
							.version(nodeInfo.getVersion())
							.registerDate(new Date())
//							.enableStatus(EnableStatusRule.ENABLE) // ############## TODO 개발중 인증패스
							.build();
		
		// DB - Save
		nodeRepo.save(node);

		// Message
		message = BaseV1Dto.builder().sender("SERVER")
									.target(uid)
									.cmd("INFO")
									.type("NEW")
									.obj("NODE")
									.value("SUCCESS")
									.build();
		iotChannelCompV1.sendDataUID(message); // Send
		
		// Log
		log.info(BellLog.getLogHead() + "New Node Info Save (" + nodeInfo.getUid() + ")");
		
		// Return
		return true;
	}
	
	public boolean generateToken(String uid)
	{
		// Field
		BaseV1Dto message;
		Node node;
		String token;
		
		// Init
		node = nodeRepo.findByUid(uid);
		
		// Process
		if (node == null)
			message = BaseV1Dto.builder().sender("SERVER").target(uid)
										.cmd("GET")
										.type("INFO")
										.obj("NODE")
										.build();
		else
		{
			token = iotAuthCompV1.generateToken(uid);
			
			message = BaseV1Dto.builder().sender("SERVER").target(uid)
										.cmd("SET")
										.type("INFO")
										.obj("TOKEN")
										.value(token)
										.build();
		}
		
		// Send
		iotChannelCompV1.sendDataUID(message);
		
		// Return
		return true;
	}
}
