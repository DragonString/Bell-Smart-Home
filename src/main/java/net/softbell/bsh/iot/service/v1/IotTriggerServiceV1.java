package net.softbell.bsh.iot.service.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.TriggerLastStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.domain.entity.NodeTriggerItem;
import net.softbell.bsh.domain.entity.NodeTriggerOccurAction;
import net.softbell.bsh.domain.entity.NodeTriggerRestoreAction;
import net.softbell.bsh.domain.repository.NodeActionRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeTriggerItemRepo;
import net.softbell.bsh.domain.repository.NodeTriggerOccurActionRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRestoreActionRepo;
import net.softbell.bsh.dto.request.IotTriggerDto;
import net.softbell.bsh.dto.request.IotTriggerItemDto;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Trigger 서비스
 */
@AllArgsConstructor
@Service
public class IotTriggerServiceV1
{
	// Global Field
	private final MemberService memberService;
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	private final NodeTriggerRepo nodeTriggerRepo;
	private final NodeTriggerItemRepo nodeTriggerItemRepo;
	private final NodeTriggerOccurActionRepo nodeTriggerOccurActionRepo;
	private final NodeTriggerRestoreActionRepo nodeTriggerRestoreActionRepo;
	private final NodeActionRepo nodeActionRepo;
	private final NodeItemRepo nodeItemRepo;
	
	public List<NodeTrigger> getAllTriggers(Authentication auth)
	{
		return nodeTriggerRepo.findAll();
	}
	
	@Transactional
	public boolean createTrigger(Authentication auth, IotTriggerDto iotTriggerDto)
	{
		// Exception
		if (iotTriggerDto == null)
			return false;
		
		// Field
		Member member;
		NodeTrigger nodeTrigger;
		HashMap<Long, IotTriggerItemDto> mapTriggerItem;
		List<NodeTriggerItem> listTriggerItem;
		List<NodeTriggerOccurAction> listOccurAction;
		List<NodeTriggerRestoreAction> listRestoreAction;
		EnableStatusRule enableStatus;
		
		// Init
		listTriggerItem = new ArrayList<NodeTriggerItem>();
		listOccurAction = new ArrayList<NodeTriggerOccurAction>();
		listRestoreAction = new ArrayList<NodeTriggerRestoreAction>();
		
		member = memberService.getMember(auth.getName());
		mapTriggerItem = iotTriggerDto.getMapTriggerItem();
		
		if (iotTriggerDto.isEnableStatus())
			enableStatus = EnableStatusRule.ENABLE;
		else
			enableStatus = EnableStatusRule.DISABLE;
		
		// Exception
		if (member == null)
			return false;
		
		// Data Process - Node Trigger
		nodeTrigger = NodeTrigger.builder()
										.enableStatus(enableStatus)
										.description(iotTriggerDto.getDescription())
										.expression(iotTriggerDto.getExpression())
										.member(member)
										.lastStatus(TriggerLastStatusRule.WAIT)
										.build();
		
		// DB - Node Trigger Save
		nodeTriggerRepo.save(nodeTrigger);
		
		
		// Data Process - Node Trigger Item
		if (mapTriggerItem != null)
		{
			mapTriggerItem.forEach((key, value) ->
			{
				if(value.getItemId() != null && value.getItemId() != 0)
				{
					// Field
					Optional<NodeItem> optNodeItem;
					NodeTriggerItem nodeTriggerItem;
					
					// Init
					optNodeItem = nodeItemRepo.findById(value.getItemId());
					
					// Build
					if (optNodeItem.isPresent())
					{
						nodeTriggerItem = NodeTriggerItem.builder()
														.nodeTrigger(nodeTrigger)
														.nodeItem(optNodeItem.get())
														.alias(value.getAlias())
															.build();
						
						// List Add
						listTriggerItem.add(nodeTriggerItem);
					}
				}
			});
			nodeTrigger.setNodeTriggerItems(listTriggerItem);
		}
		
		// DB - Node Trigger Items Save
		nodeTriggerItemRepo.saveAll(listTriggerItem);
		
		
		// Data Process - Node Trigger Occur Action
		for (long actionId : iotTriggerDto.getOccurActionId())
		{
			// Field
			Optional<NodeAction> optNodeAction;
			NodeTriggerOccurAction nodeTriggerOccurAction;
			
			// Init
			optNodeAction = nodeActionRepo.findById(actionId);
			
			// Exception
			if (!optNodeAction.isPresent())
				continue;
			
			// Process
			nodeTriggerOccurAction = NodeTriggerOccurAction.builder()
																.nodeTrigger(nodeTrigger)
																.nodeAction(optNodeAction.get())
																.build();
			
			// List Add
			listOccurAction.add(nodeTriggerOccurAction);
		}
		nodeTrigger.setNodeTriggerOccurActions(listOccurAction);
		
		// DB - Node Trigger Occure Action Save
		nodeTriggerOccurActionRepo.saveAll(listOccurAction);
		
		
		// Data Process - Node Trigger Restore Action
		for (long actionId : iotTriggerDto.getRestoreActionId())
		{
			// Field
			Optional<NodeAction> optNodeAction;
			NodeTriggerRestoreAction nodeTriggerRestoreAction;
			
			// Init
			optNodeAction = nodeActionRepo.findById(actionId);
			
			// Exception
			if (!optNodeAction.isPresent())
				continue;
			
			// Process
			nodeTriggerRestoreAction = NodeTriggerRestoreAction.builder()
																.nodeTrigger(nodeTrigger)
																.nodeAction(optNodeAction.get())
																.build();
			
			// List Add
			listRestoreAction.add(nodeTriggerRestoreAction);
		}
		nodeTrigger.setNodeTriggerOccurActions(listOccurAction);
		
		// DB - Node Trigger Restore Action Save
		nodeTriggerRestoreActionRepo.saveAll(listRestoreAction);
		
		
		// Return
		return true;
	}
}
