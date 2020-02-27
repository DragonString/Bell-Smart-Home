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
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.ItemCategoryRule;
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
	private final IotNodeServiceV1 nodeService;
	
	private final NodeItemRepo nodeItemRepo;
	private final NodeActionRepo nodeActionRepo;
	private final NodeActionItemRepo nodeActionItemRepo;
	
	public List<NodeItem> getAvailableNodeItem(Authentication auth)
	{
		// Field
		List<NodeItem> listNodeItem;
		
		// Init
		listNodeItem = nodeService.getCategoryNodeItems(auth, GroupRole.ACTION, ItemCategoryRule.CONTROL);
		
		// Return
		return listNodeItem;
	}
	
	public List<NodeAction> getAllNodeActions(Authentication auth)
	{
		// Field
		Member member;
		List<NodeAction> listNodeAction;
		
		// Init
		member = memberService.getMember(auth.getName());
		
		// Exception
		if (memberService.isAdmin(member))
			listNodeAction = nodeActionRepo.findAll();
		else
			listNodeAction = nodeActionRepo.findByMember(member);
		
		// Return
		return listNodeAction;
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
	
	public boolean execAction(long actionId, Authentication auth)
	{
		// Field
		Member member;
		Optional<NodeAction> optNodeAction;
		boolean isSuccess = true;
		
		// Init
		member = memberService.getMember(auth.getName());
		optNodeAction = nodeActionRepo.findById(actionId);
		
		// Exception
		if (!optNodeAction.isPresent())
			return false;
		
		// Process
		isSuccess = execAction(optNodeAction.get(), member);
		
		// Return
		return isSuccess;
	}
	
	public boolean execAction(NodeAction nodeAction, Member member)
	{
		// Field
		List<NodeActionItem> listNodeActionItem;
		boolean isSuccess = true;
		
		// Exception
		if (nodeAction == null)
			return false;
		
		// Permission
		if (!memberService.isAdmin(member)) // 관리자가 아닌데
			if (!nodeAction.getMember().equals(member)) // 액션 제작자가 아니면
				return false; // 수행 중단
		
		// Load
		listNodeActionItem = nodeAction.getNodeActionItems();
		
		// Process
		for (NodeActionItem actionItem : listNodeActionItem)
			if (!nodeService.setItemValue(actionItem.getNodeItem(), actionItem.getItemStatus()))
				isSuccess = false;
		
		// Return
		return isSuccess;
	}
}
