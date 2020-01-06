package net.softbell.bsh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.dto.iot.BSHPv1DTO;
import net.softbell.bsh.libs.BellLog;

@Service
public class IotService {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private NodeRepo nodeRepo;
	
	
	public void sendMessage(BSHPv1DTO message)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + message.getTarget() + "로 메시지 전송");
		String temp = message.getTarget();
//		message.setTarget("3213");
		// Process
		template.convertAndSend("/api/stomp/queue/iot/v1/node/" + temp, message);
	}
	
	public boolean procMessage(BSHPv1DTO message)
	{
		// Field
		boolean isSuccess = false;
		
		System.out.println(message.toString());
		
		// Process
		if (message.getTarget().equals("SERVER"))
			if (message.getCmd().equals("GET"))
				isSuccess = procMessageGet(message);
			else if (message.getCmd().equals("SET"))
				isSuccess = procMessageSet(message);
		
		// Exception
		if (!isSuccess)
		{
			G_Logger.error(BellLog.getLogHead() + "처리되지 않은 메시지: " + message.toString());
			sendMessage(BSHPv1DTO.builder().sender("SERVER").target(message.getSender()).cmd("INFO").type("HANDLE").obj("ERROR").build());
		}
		else
			G_Logger.info(BellLog.getLogHead() + "처리된 메시지: " + message.toString());
		
		// Return
		return isSuccess;
	}
	
	private boolean procMessageGet(BSHPv1DTO message)
	{
		
		return false;
	}
	
	private boolean procMessageSet(BSHPv1DTO message)
	{
		if (message.getType().equals("INFO"))
			if (message.getObj().equals("CHIPID"))
			{
				
			}
			else if (message.getObj().equals("ITEMS"))
			{
				
			}
			else if (message.getObj().equals("ITEM"))
			{
				
			}
			else if (message.getObj().equals("MODE"))
			{
				
			}
		else if (message.getType().equals("ITEM"))
			;
		else if (message.getType().equals("MODE"))
			;
		
		return false;
	}
}
