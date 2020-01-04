package net.softbell.bsh.controller.stomp.iot.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.dto.iot.BSHPv1DTO;
import net.softbell.bsh.service.IotService;

/**
 * STOMP Controller
 * @author Bell
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/sub
 */
@RestController
public class ControlStompAPI
{
	// Global Field
	@Autowired
	private IotService iotService;
	
	
	@MessageMapping("/iot/v1/node/{chipId}") // publish
	public void NodeHandler(@DestinationVariable("chipId") String chipId,
								BSHPv1DTO message)
	{
		iotService.procMessage(message);
	}
	
	@SubscribeMapping("/iot/v1/node/{chipId}")
	public BSHPv1DTO NodeSubscribeHandler(@DestinationVariable("chipId") String chipId)
	{
		// Field
		List<BSHPv1DTO> listMsg;
		BSHPv1DTO msgInfoReturn;
		
		// Init
		listMsg = new ArrayList<BSHPv1DTO>();
		msgInfoReturn = BSHPv1DTO.builder().sender("SERVER").target(chipId).cmd("INFO").type("CONNECTION").obj("SUCCESS").build();
		listMsg.add(BSHPv1DTO.builder().sender("SERVER").target(chipId).cmd("GET").type("INFO").obj("ITEMS").build());
		listMsg.add(BSHPv1DTO.builder().sender("SERVER").target(chipId).cmd("GET").type("MODE").obj("NODE").build());
		listMsg.add(BSHPv1DTO.builder().sender("SERVER").target(chipId).cmd("INFO").type("CONNECTION").obj("SUCCESS").build());
		
		// Process
		for (BSHPv1DTO message : listMsg)
			iotService.sendMessage(message);
		
		// Log
		System.out.println(chipId + "번 노드 구독됨?"); // test
		
		// Return
		return msgInfoReturn;
	}
}
