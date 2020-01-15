package net.softbell.bsh.iot.service.v1;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.PinModeRule;
import net.softbell.bsh.domain.PinTypeRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.dto.response.ResultDto;
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
@Service
public class IotTokenServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotChannelCompV1 iotChannelCompV1;
	@Autowired
	private IotAuthCompV1 iotAuthCompV1;
	
	@Autowired
	private NodeRepo nodeRepo;
	@Autowired
	private NodeItemRepo nodeItemRepo;
	@Autowired
	private NodeItemHistoryRepo nodeItemHistoryRepo;

	
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
		
		// DB - Save
		nodeRepo.save(node);
		
		// Log
		G_Logger.info(BellLog.getLogHead() + "Node Info Save (" + node.getUid() + ")");
		
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
			if (nodeItem.getPinId() == itemInfo.getPinId()) // DB Update
			{
				nodeItem.setControlMode(itemInfo.getControlMode());
				nodeItem.setPinId(itemInfo.getPinId());
				nodeItem.setPinMode(PinModeRule.ofLegacyCode(itemInfo.getPinMode()));
				nodeItem.setPinName(itemInfo.getPinName());
				nodeItem.setPinType(PinTypeRule.ofLegacyCode(itemInfo.getPinType()));
				
				nodeItemRepo.save(nodeItem);
				G_Logger.info(BellLog.getLogHead() + "Node Item Save (" + node.getUid() + "-" + nodeItem.getPinName() + ")");
				
				return true;
			}
		
		// DB - Insert data
		NodeItem nodeItem = NodeItem.builder()
							.node(node)
							.controlMode(itemInfo.getControlMode())
							.pinId(itemInfo.getPinId())
							.pinMode(PinModeRule.ofLegacyCode(itemInfo.getPinMode()))
							.pinName(itemInfo.getPinName())
							.pinType(PinTypeRule.ofLegacyCode(itemInfo.getPinType()))
							.alias(itemInfo.getPinName())
							.build();
		nodeItemRepo.save(nodeItem);

		// Log
		G_Logger.info(BellLog.getLogHead() + "Node New Item Save (" + node.getUid() + "-" + nodeItem.getPinName() + ")");
		
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
		
		nodeItem = nodeItemRepo.findByNodeAndPinId(node, itemValue.getPinId());
		
		if (nodeItem == null)
			return false;
		
		// Process
		nodeItemHistory = NodeItemHistory.builder()
										.nodeItem(nodeItem)
										.pinStatus(itemValue.getPinStatus())
										.receiveDate(new Date())
										.build();
		
		// DB - Save
		nodeItemHistoryRepo.save(nodeItemHistory);
		
		// Return
		return true;
	}
}
