package net.softbell.bsh.iot.service.v1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 구독 서비스
 */
@Service
public class IotSubscribeServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotChannelCompV1 iotChannelCompV1;
	@Autowired
	private NodeRepo nodeRepo;

	
	public BaseV1Dto procTopicSubscribe()
	{
		// Field
		BaseV1Dto data;
		
		// Init
		data = BaseV1Dto.builder().sender("SERVER")
									.target("NODE")
									.cmd("INFO")
									.type("CONNECTION")
									.obj("NODE")
									.value("SUCCESS")
									.build();
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "Node Topic Channel Subscribe");
		
		// Return
		return data;
	}
	
	public BaseV1Dto procUIDSubscribe(String uid)
	{
		// Field
		BaseV1Dto data;
		
		// Init
		data = BaseV1Dto.builder().sender("SERVER")
									.target(uid)
									.cmd("INFO")
									.type("CONNECTION")
									.obj("UID")
									.value("SUCCESS")
									.build();
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "Node UID Channel Subscribe (" + uid + ")");
		
		// Return
		return data;
	}
	
	public BaseV1Dto procTokenSubscribe(String token)
	{
		// Field
		Node node;
		List<BaseV1Dto> listMsg;
		BaseV1Dto msgInfo;
		
		// Init
		node = nodeRepo.findByToken(token);
		listMsg = new ArrayList<BaseV1Dto>();
		listMsg.add(BaseV1Dto.builder().sender("SERVER").target(token).cmd("GET").type("INFO").obj("NODE").build());
		listMsg.add(BaseV1Dto.builder().sender("SERVER").target(token).cmd("GET").type("INFO").obj("ITEMS").build());
		msgInfo = BaseV1Dto.builder().sender("SERVER").target(token).cmd("INFO").type("CONNECTION").obj("TOKEN").value("SUCCESS").build();
		
		// Exception
		if (node == null)
			msgInfo.setValue("REJECT");
		
		// Process
		for (BaseV1Dto message : listMsg)
			iotChannelCompV1.sendDataToken(message);

		// Log
		G_Logger.info(BellLog.getLogHead() + "Node Token Channel Subscribe (" + token + ")");
		
		// Return
		return msgInfo;
	}
}
