package net.softbell.bsh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.dto.bshp.BaseV1DTO;
import net.softbell.bsh.dto.bshp.ItemInfoV1DTO;
import net.softbell.bsh.dto.bshp.ItemValueV1DTO;
import net.softbell.bsh.dto.bshp.NodeInfoV1DTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 서비스
 */
@Service
public class IotService {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private NodeRepo nodeRepo;
	
	
	public void sendMessage(BaseV1DTO message)
	{
		// Log
		G_Logger.info(BellLog.getLogHead() + message.getTarget() + "로 메시지 전송");
		System.out.println("기본목적지: " + template.getDefaultDestination());
		System.out.println("유저목적지: " + template.getUserDestinationPrefix());
		
		// Process
		template.convertAndSend("/api/stomp/queue/iot/v1/node/token/" + message.getTarget(), message);
	}
	
	public boolean setNodeInfo(NodeInfoV1DTO nodeInfo)
	{
		// FIeld
		Node node;
		
		
		
		// Init
		node = Node.builder().uid(nodeInfo.getUid())
							.token(nodeInfo.getToken())
							.controlMode(nodeInfo.getControlMode())
							.nodeName(nodeInfo.getNodeName())
							.build();
		
		
		
		return true;
	}
	
	public boolean setItemInfo(ItemInfoV1DTO itemInfo)
	{
		
		return true;
	}
	
	public boolean setItemValue(ItemValueV1DTO itemValue)
	{
		
		return true;
	}
	
	public boolean procMessage(BaseV1DTO message)
	{
		// Field
		boolean isSuccess = false;
		
		System.out.println(message.toString()); // TODO TEST
		
		// Exception
		if (message == null || 
				message.getSender() == null || message.getSender().isEmpty() || 
				message.getTarget() == null || message.getTarget().isEmpty())
			return true;
		
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
			sendMessage(BaseV1DTO.builder().sender("SERVER").target(message.getSender()).cmd("INFO").type("HANDLE").obj("ERROR").build());
		}
		else
			G_Logger.info(BellLog.getLogHead() + "처리된 메시지: " + message.toString());
		
		// Return
		return isSuccess;
	}
	
	private boolean procMessageGet(BaseV1DTO message)
	{
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private boolean procMessageSet(BaseV1DTO message)
	{
		if (message.getType().equals("INFO"))
			if (message.getObj().equals("ITEMS"))
			{
				// Log
				System.out.println("되나?");
//				System.out.println(message.getValues().getClass());
//				System.out.println("-----------------");
//				System.out.println(message.getValues().toString());
//				System.out.println("---------end---------");
				
//				for (Object value : message.getValues())
//					System.out.println("What? " + value);
				
				// Field
//				InfoItemV1DTO[] listItem;
				List<Object> listItem;
				Gson gson = new Gson();
				
				// Init
//				listItem = (InfoItemV1DTO[]) message.getValues();
//				listItem = message.getValues();
//				listItem = gson.fromJson((String) message.getValues(), InfoItemV1DTO[].class);
//				listItem = (InfoItemV1DTO[])message.getValues();
//				System.out.println("Hmm? " + listItem);
				// Save
//				for (Object value : listItem)
//					System.out.println("pinName: " + ((ItemInfoV1DTO)value).getPinName());
			}
			else if (message.getObj().equals("ITEM"))
			{
//				InfoItemV1DTO test = message.getItem();
//				System.out.println("pinNAme: " + test.getPinName());
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
