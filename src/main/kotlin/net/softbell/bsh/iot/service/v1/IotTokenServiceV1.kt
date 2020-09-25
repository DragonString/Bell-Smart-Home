package net.softbell.bsh.iot.service.v1;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemModeRule;
import net.softbell.bsh.domain.ItemTypeRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Token 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotTokenServiceV1
{
	// Global Field
	private final IotTriggerServiceV1 iotTriggerService;
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	
	private final NodeRepo nodeRepo;
	private final NodeItemRepo nodeItemRepo;
	private final NodeItemHistoryRepo nodeItemHistoryRepo;

	
	private Node getNormalTokenNode(String token)
	{
		// Field
		Node node;
		
		// Init
		node = nodeRepo.findByToken(token);
		
		// Exception
		if (node == null) // 토큰에 해당하는 노드가 없다면
		{
			// Field
			BaseV1Dto data;
			
			// Init
			data = BaseV1Dto.builder().sender("SERVER")
							.target(token)
							.cmd("INFO")
							.type("CONNECTION")
							.obj("NODE")
							.value("FAIL")
							.build();
			
			// Send
			iotChannelCompV1.sendDataToken(data); // 연결 실패 메시지 전송
			return null;
		}
		if (!iotAuthCompV1.isApprovalNode(node))
			return null;
		
		// Return
		return node;
	}

	@Transactional
	public boolean setNodeInfo(String token, NodeInfoV1Dto nodeInfo)
	{
		// Field
		Node node;
		
		// Init
		node = getNormalTokenNode(token);
		
		// Exception
		if (node == null)
			return false;
		if (!node.getUid().equals(nodeInfo.getUid()))
			return false; // 서버상 UID와 수신된 UID가 다르면 해킹으로 간주
		
		// Process
		node.setControlMode(nodeInfo.getControlMode());
		node.setNodeName(nodeInfo.getNodeName());
		node.setVersion(nodeInfo.getVersion());
		
		// DB - Save
		nodeRepo.save(node);
		
		// Log
		log.info(BellLog.getLogHead() + "Node Info Save (" + node.getUid() + ")");
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean setItemInfo(String token, ItemInfoV1Dto itemInfo)
	{
		// Field
		Node node;
		
		// Init
		node = getNormalTokenNode(token);
		
		// Exception
		if (node == null)
			return false;
		
		// Process
		for (NodeItem nodeItem : node.getNodeItems()) // TODO 불필요한 이 반복 부분 제거하고 NodeItemRepo에 조회절 추가하기
			if (nodeItem.getItemIndex() == itemInfo.getItemIndex()) // DB Update
			{
				nodeItem.setControlMode(itemInfo.getControlMode());
				nodeItem.setItemIndex(itemInfo.getItemIndex());
				nodeItem.setItemCategory(ItemCategoryRule.ofLegacyCode(itemInfo.getItemCategory()));
				nodeItem.setItemMode(ItemModeRule.ofLegacyCode(itemInfo.getItemMode()));
				nodeItem.setItemName(itemInfo.getItemName());
				nodeItem.setItemType(ItemTypeRule.ofLegacyCode(itemInfo.getItemType()));
				
				nodeItemRepo.save(nodeItem);
				log.info(BellLog.getLogHead() + "Node Item Save (" + node.getUid() + "-" + nodeItem.getItemName() + ")");
				
				return true;
			}
		
		// DB - Insert data
		NodeItem nodeItem = NodeItem.builder()
							.node(node)
							.controlMode(itemInfo.getControlMode())
							.itemIndex(itemInfo.getItemIndex())
							.itemCategory(ItemCategoryRule.ofLegacyCode(itemInfo.getItemCategory()))
							.itemMode(ItemModeRule.ofLegacyCode(itemInfo.getItemMode()))
							.itemName(itemInfo.getItemName())
							.itemType(ItemTypeRule.ofLegacyCode(itemInfo.getItemType()))
							.alias(itemInfo.getItemName())
							.build();
		nodeItemRepo.save(nodeItem);

		// Log
		log.info(BellLog.getLogHead() + "Node New Item Save (" + node.getUid() + "-" + nodeItem.getItemName() + ")");
		
		// Return
		return true;
	}
	
	public void reqItemValue(String token, int pin)
	{
		// Field
		BaseV1Dto data;
		
		// Init
		data = BaseV1Dto.builder().sender("SERVER")
						.target(token)
						.cmd("GET")
						.type("VALUE")
						.obj("ITEM")
						.value(pin)
						.build();
		
		// Send
		iotChannelCompV1.sendDataToken(data);
	}
	
	@Transactional
	public boolean setItemValue(String token, ItemValueV1Dto itemValue)
	{
		// Field
		Node node;
		NodeItem nodeItem;
		NodeItemHistory nodeItemHistory;
		
		// Init
		node = getNormalTokenNode(token); // TODO 이것도 token, uid로 한번에 검색되게
		
		// Exception
		if (node == null)
			return false;
		if (itemValue.getItemStatus() == null)
		{
			log.error(BellLog.getLogHead() + "ItemStatus 수신 데이터 없음 (" + node.getAlias() + ")");
			return false;
		}
		
		nodeItem = nodeItemRepo.findByNodeAndItemIndex(node, itemValue.getItemIndex());
		
		if (nodeItem == null)
			return false;
		
		// Process
		nodeItemHistory = NodeItemHistory.builder()
										.nodeItem(nodeItem)
										.itemStatus(itemValue.getItemStatus())
										.receiveDate(new Date())
										.build();
		
		// DB - Save
		nodeItemHistoryRepo.save(nodeItemHistory);
		
		// Trigger Check
		iotTriggerService.procTrigger(nodeItem);
		
		// Return
		return true;
	}
}
