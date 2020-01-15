package net.softbell.bsh.iot.service.v1;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotControlServiceV1
{
	// Global Field
	private final IotMessageServiceV1 iotMessageService;
	private final IotChannelCompV1 iotChannelCompV1;
	private final NodeItemRepo nodeItemRepo;
	
	public boolean setItemValue(long itemId, short itemValue)
	{
		// Field
		Optional<NodeItem> optNodeItem;
		BaseV1Dto baseMessage;
		ItemValueV1Dto itemValueData;
		
		// Init
		optNodeItem = nodeItemRepo.findById(itemId);
		
		// Exception
		if (!optNodeItem.isPresent())
			return false;
		
		// Process
		itemValueData = ItemValueV1Dto.builder().pinId(optNodeItem.get().getPinId()).pinStatus(itemValue).build();
		baseMessage = iotMessageService.getBaseMessage(optNodeItem.get().getNode().getToken(), "SET", "VALUE", "ITEM", itemValueData);
		
		// Send
		iotChannelCompV1.sendDataToken(baseMessage);
		
		// Return
		return true;
	}
}
