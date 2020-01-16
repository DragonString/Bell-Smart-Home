package net.softbell.bsh.iot.service.v1;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotNodeServiceV1
{
	// Global Field
	private final IotMessageServiceV1 iotMessageService;
	private final IotChannelCompV1 iotChannelCompV1;
	private final NodeRepo nodeRepo;
	private final NodeItemRepo nodeItemRepo;
	private final NodeItemHistoryRepo nodeItemHistoryRepo;
	
	public Page<Node> getAllNodes(int intPage, int intCount)
	{
		// Field
		Pageable curPage;
		
		// Init
		curPage = PageRequest.of(intPage - 1, intCount);
		
		// Return
		return nodeRepo.findAll(curPage);
	}
	
	public Node getNode(long nodeId)
	{
		// Field
		Optional<Node> optNode;
		
		// Init
		optNode = nodeRepo.findById(nodeId);
		
		// Return
		if (optNode.isPresent())
			return optNode.get();
		else
			return null;
	}
	
	public List<NodeItemHistory> getNodeItemHistory(long nodeItemId)
	{
		// Field
		List<NodeItemHistory> listNodeItemHistory;
		Pageable curPage;
		Optional<NodeItem> optNodeItem;
		
		// Init
		optNodeItem = nodeItemRepo.findById(nodeItemId);
		
		// Exception
		if (!optNodeItem.isPresent())
			return null;
		
		// Process
		curPage = PageRequest.of(0, 50, new Sort(Direction.DESC, "itemHistoryId")); // TODO 임시로 50개 뽑게 설정함
		listNodeItemHistory = nodeItemHistoryRepo.findByNodeItem(optNodeItem.get(), curPage);
		Collections.reverse(listNodeItemHistory);
		
		// Return
		return listNodeItemHistory;
	}
	
	public boolean setItemValue(long itemId, short itemValue)
	{
		// Field
		Optional<NodeItem> optNodeItem;
		BaseV1Dto baseMessage;
		BaseV1Dto getValueMessage;
		ItemValueV1Dto itemValueData;
		String token;
		byte pinId;
		
		// Init
		optNodeItem = nodeItemRepo.findById(itemId);
		
		// Exception
		if (!optNodeItem.isPresent())
			return false;
		
		// Process
		token = optNodeItem.get().getNode().getToken();
		pinId = optNodeItem.get().getPinId();
		itemValueData = ItemValueV1Dto.builder().pinId(pinId).pinStatus(itemValue).build();
		baseMessage = iotMessageService.getBaseMessage(token, "SET", "VALUE", "ITEM", itemValueData);
		getValueMessage = iotMessageService.getBaseMessage(token, "GET", "VALUE", "ITEM", pinId);
		
		// Send5
		iotChannelCompV1.sendDataToken(baseMessage);
		iotChannelCompV1.sendDataToken(getValueMessage);
		
		// Return
		return true;
	}
}
