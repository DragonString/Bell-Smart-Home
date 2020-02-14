package net.softbell.bsh.iot.service.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.TriggerLastStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.domain.entity.NodeTriggerOccurAction;
import net.softbell.bsh.domain.entity.NodeTriggerRestoreAction;
import net.softbell.bsh.domain.repository.NodeActionRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeTriggerOccurActionRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRestoreActionRepo;
import net.softbell.bsh.dto.request.IotTriggerDto;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.component.v1.IotTriggerParserCompV1;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Trigger 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotTriggerServiceV1
{
	// Global Field
	private final MemberService memberService;
	private final IotActionServiceV1 iotActionService;
	
	private final IotTriggerParserCompV1 iotTriggerParserComp;
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	
	private final NodeTriggerRepo nodeTriggerRepo;
	private final NodeTriggerOccurActionRepo nodeTriggerOccurActionRepo;
	private final NodeTriggerRestoreActionRepo nodeTriggerRestoreActionRepo;
	private final NodeActionRepo nodeActionRepo;
	private final NodeItemRepo nodeItemRepo;
	
	public List<NodeTrigger> getAllTriggers(Authentication auth)
	{
		return nodeTriggerRepo.findAll();
	}
	
	public NodeTrigger getTrigger(Authentication auth, long triggerId)
	{
		// Field
		Optional<NodeTrigger> optNodeTrigger;
		
		// Init
		optNodeTrigger = nodeTriggerRepo.findById(triggerId);
		
		// Exception
		if (!optNodeTrigger.isPresent())
			return null;
		
		// Return
		return optNodeTrigger.get();
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
		List<NodeTriggerOccurAction> listOccurAction;
		List<NodeTriggerRestoreAction> listRestoreAction;
		EnableStatusRule enableStatus;
		
		// Init
		listOccurAction = new ArrayList<NodeTriggerOccurAction>();
		listRestoreAction = new ArrayList<NodeTriggerRestoreAction>();
		
		member = memberService.getMember(auth.getName());
		
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
	
	@Transactional
	public boolean modifyTrigger(Authentication auth, long triggerId, IotTriggerDto iotTriggerDto)
	{
		// Field
		
		// Init
		
		// Process
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean deleteTrigger(Authentication auth, long triggerId)
	{
		// Field
		Optional<NodeTrigger> optNodeTrigger;
		NodeTrigger nodeTrigger;
		
		// Init
		optNodeTrigger = nodeTriggerRepo.findById(triggerId);
		
		// Exception
		if (!optNodeTrigger.isPresent())
			return false;
		nodeTrigger = optNodeTrigger.get();
		
		// DB - Delete
		for (NodeTriggerOccurAction entity : nodeTrigger.getNodeTriggerOccurActions())
			nodeTriggerOccurActionRepo.delete(entity);
		for (NodeTriggerRestoreAction entity : nodeTrigger.getNodeTriggerRestoreActions())
			nodeTriggerRestoreActionRepo.delete(entity);
		nodeTriggerRepo.delete(nodeTrigger);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean procTrigger(NodeItem nodeItem)
	{
		// Field
		boolean isSuccess;
		List<NodeTrigger> listNodeTrigger;
		List<NodeAction> listNodeAction; 
		
		// Init
		isSuccess = true;
		listNodeTrigger = iotTriggerParserComp.convTrigger(nodeItem);
		listNodeAction = new ArrayList<NodeAction>();
		
		// Process
		for (NodeTrigger nodeTrigger : listNodeTrigger)
		{
			// Field
			Boolean isOccur;
			
			// Init
			isOccur = iotTriggerParserComp.parseEntity(nodeTrigger);
			
			// Exception
			if (isOccur == null)
			{
				nodeTrigger.setLastStatus(TriggerLastStatusRule.ERROR); // DB Update - Error
				continue;
			}
			if ((nodeTrigger.getLastStatus() == TriggerLastStatusRule.OCCUR && isOccur) ||
					(nodeTrigger.getLastStatus() == TriggerLastStatusRule.RESTORE && !isOccur))
				continue;
			
			// Load
			listNodeAction.addAll(iotTriggerParserComp.getTriggerAction(nodeTrigger, isOccur));
			
			// DB - Update
			if (isOccur)
				nodeTrigger.setLastStatus(TriggerLastStatusRule.OCCUR);
			else
				nodeTrigger.setLastStatus(TriggerLastStatusRule.RESTORE);
		}
		
		// Exec Action
		for (NodeAction nodeAction : listNodeAction)
			if (!iotActionService.execAction(nodeAction))
				isSuccess = false;
		
		// Return
		return isSuccess;
	}
	
	
}
