package net.softbell.bsh.iot.service.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeActionItem;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeActionItemRepo;
import net.softbell.bsh.domain.repository.NodeActionRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.dto.request.IotActionDto;
import net.softbell.bsh.dto.request.IotActionItemDto;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotActionServiceV1
{
	// Global Field
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeServiceV1;
	private final NodeItemRepo nodeItemRepo;
	private final NodeActionRepo nodeActionRepo;
	private final NodeActionItemRepo nodeActionItemRepo;
	
	public List<NodeItem> getAvailableNodeItem(Authentication auth)
	{
		// Field
		List<NodeItem> listNodeItem;
		
		// TODO 계정에서 접근가능한 아이템만 반환하도록 추가해야됨.. 나중에... 언젠가는 추가하겠지...?
		// Init
		listNodeItem = nodeItemRepo.findAll();
		
		// Return
		return listNodeItem;
	}
	
	public List<NodeAction> getAllNodeActions(Authentication auth)
	{
		// Field
		return nodeActionRepo.findAll();
	}
	
	public NodeAction getNodeAction(Authentication auth, long actionId)
	{
		// Field
		Optional<NodeAction> optNodeAction;
		NodeAction nodeAction;
		
		// Init
		optNodeAction = nodeActionRepo.findById(actionId);
		
		// Exception
		if (!optNodeAction.isPresent())
			return null;
		
		// Load
		nodeAction = optNodeAction.get();
		
		// Return
		return nodeAction;
	}
	
	@Transactional
	public boolean createAction(Authentication auth, IotActionDto iotActionDto)
	{
		// Log
		log.info(BellLog.getLogHead() + "Creating Action (" + iotActionDto.getDescription() + ")");
		
		// Field
		Member member;
		NodeAction nodeAction;
		HashMap<Long, IotActionItemDto> mapActionItem;
		List<NodeActionItem> listNodeActionItem;
		EnableStatusRule enableStatus;
		
		// Init
		member = memberService.getMember(auth.getName());
		listNodeActionItem = new ArrayList<NodeActionItem>();
		mapActionItem = iotActionDto.getMapActionItem();
		if (iotActionDto.isEnableStatus())
			enableStatus = EnableStatusRule.ENABLE;
		else
			enableStatus = EnableStatusRule.DISABLE;
		
		// Exception
		if (member == null)
			return false;
		
		// Data Process - Action Info
		nodeAction = NodeAction.builder()
								.enableStatus(enableStatus)
								.description(iotActionDto.getDescription())
								.member(member)
									.build();

		// Data Process - Action Item Info
		if (mapActionItem != null)
		{
			mapActionItem.forEach((key, value) ->
			{
				if(value.getItemId() != null && value.getItemId() != 0)
				{
					// Field
					Optional<NodeItem> optNodeItem;
					NodeActionItem nodeActionItem;
					
					// Init
					optNodeItem = nodeItemRepo.findById(value.getItemId());
					
					// Build
					if (optNodeItem.isPresent())
					{
						nodeActionItem = NodeActionItem.builder()
														.nodeItem(optNodeItem.get())
														.itemStatus(value.getItemStatus())
														.nodeAction(nodeAction)
															.build();
						
						// List Add
						listNodeActionItem.add(nodeActionItem);
					}
				}
			});
			nodeAction.setNodeActionItems(listNodeActionItem);
		}

		// DB - Save
		nodeActionRepo.save(nodeAction);
		nodeActionItemRepo.saveAll(listNodeActionItem);
		
		// Log
		log.info(BellLog.getLogHead() + "Created Action (" + nodeAction.getActionId() + ", " + iotActionDto.getDescription() + ")");
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean modifyAction(Authentication auth, long actionId, IotActionDto iotActionDto)
	{
		// Field
		Optional<NodeAction> optNodeAction;
		NodeAction nodeAction;
		HashMap<Long, IotActionItemDto> mapActionItem;
		List<NodeActionItem> listNodeActionItem;
		EnableStatusRule enableStatus;
		
		// Init
		optNodeAction = nodeActionRepo.findById(actionId);
		mapActionItem = iotActionDto.getMapActionItem();
		listNodeActionItem = new ArrayList<NodeActionItem>();
		if (iotActionDto.isEnableStatus())
			enableStatus = EnableStatusRule.ENABLE;
		else
			enableStatus = EnableStatusRule.DISABLE;
		
		// Exception
		if (!optNodeAction.isPresent())
			return false;
		
		// Load
		nodeAction = optNodeAction.get();
//		listNodeActionItem = nodeAction.getNodeActionItems();
		
		// DB - Action Item Clear
		nodeActionItemRepo.deleteAll(nodeAction.getNodeActionItems());
		nodeActionItemRepo.flush();
//		nodeActionRepo.flush();
//		for (NodeActionItem value : listNodeActionItem)
//			nodeAction.removeNodeActionItem(value);
		
		// Data Process - Item Info
		nodeAction.setNodeActionItems(null);
		nodeAction.setEnableStatus(enableStatus);
		nodeAction.setDescription(iotActionDto.getDescription());
		
		// DB - Save
		nodeActionRepo.save(nodeAction);
		
		// Data Process - Action Item Info
		if (mapActionItem != null)
		{
			mapActionItem.forEach((key, value) ->
			{
				if(value.getItemId() != null && value.getItemId() != 0)
				{
					// Field
					Optional<NodeItem> optNodeItem;
					NodeActionItem nodeActionItem;
					
					// Init
					optNodeItem = nodeItemRepo.findById(value.getItemId());
					
					// Build
					if (optNodeItem.isPresent())
					{
						nodeActionItem = NodeActionItem.builder()
														.nodeItem(optNodeItem.get())
														.itemStatus(value.getItemStatus())
														.nodeAction(nodeAction)
															.build();
						
						// List Add
						listNodeActionItem.add(nodeActionItem);
					}
				}
			});
			nodeAction.setNodeActionItems(listNodeActionItem);
		}
		
		// DB - Update
//		nodeActionRepo.save(nodeAction);
		nodeActionItemRepo.saveAll(listNodeActionItem);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean deleteAction(Authentication auth, long actionId)
	{
		// Field
		Optional<NodeAction> optNodeAction;
		NodeAction nodeAction;
		
		// Init
		optNodeAction = nodeActionRepo.findById(actionId);
		
		// Exception
		if (!optNodeAction.isPresent())
			return false;
		
		// Load
		nodeAction = optNodeAction.get();
		
		// DB - Delete
		nodeActionItemRepo.deleteAll(nodeAction.getNodeActionItems());
		nodeActionRepo.delete(nodeAction);
		
		// Return
		return true;
	}
	
	public boolean execAction(long actionId)
	{
		// Field
		Optional<NodeAction> optNodeAction;
		boolean isSuccess = true;
		
		// Init
		optNodeAction = nodeActionRepo.findById(actionId);
		
		// Exception
		if (!optNodeAction.isPresent())
			return false;
		
		// Process
		isSuccess = execAction(optNodeAction.get());
		
		// Return
		return isSuccess;
	}
	
	public boolean execAction(NodeAction nodeAction)
	{
		// Field
		List<NodeActionItem> listNodeActionItem;
		boolean isSuccess = true;
		
		// Exception
		if (nodeAction == null)
			return false;
		
		// Load
		listNodeActionItem = nodeAction.getNodeActionItems();
		
		// Process
		for (NodeActionItem actionItem : listNodeActionItem)
			if (!iotNodeServiceV1.setItemValue(actionItem.getNodeItem().getItemId(), actionItem.getItemStatus()))
				isSuccess = false;
		
		// Return
		return isSuccess;
	}
}
