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
import net.softbell.bsh.domain.TriggerLastStatusRule;
import net.softbell.bsh.domain.TriggerStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.domain.entity.NodeTriggerAction;
import net.softbell.bsh.domain.repository.NodeActionRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeTriggerActionRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRepo;
import net.softbell.bsh.dto.request.IotTriggerActionDto;
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
	private final NodeTriggerActionRepo nodeTriggerActionRepo;
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
		HashMap<Long, IotTriggerActionDto> mapAction;
		List<NodeTriggerAction> listAction;
		EnableStatusRule enableStatus;
		
		// Init
		listAction = new ArrayList<NodeTriggerAction>();
		mapAction = iotTriggerDto.getMapAction();
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
		
		
		// Data Process - Node Trigger Action
		if (mapAction != null)
		{
			mapAction.forEach((key, value) ->
			{
				// Field
				Optional<NodeAction> optNodeAction;
				NodeTriggerAction nodeTriggerAction;
				
				// Init
				optNodeAction = nodeActionRepo.findById(key);
				
				// Build
				if (optNodeAction.isPresent())
				{
					nodeTriggerAction = NodeTriggerAction.builder()
															.nodeTrigger(nodeTrigger)
															.nodeAction(optNodeAction.get())
																.build();
					if (value.isEventError() && value.isEventOccur() && value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ALL);
					else if (value.isEventOccur() && value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR_AND_RESTORE);
					else if (value.isEventError())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ERROR);
					else if (value.isEventOccur())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR);
					else if (value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.RESTORE);
					
					// List Add
					listAction.add(nodeTriggerAction);
				}
			});
			nodeTrigger.setNodeTriggerActions(listAction);
		}
		
		// DB - Node Trigger Action Save
		nodeTriggerActionRepo.saveAll(listAction);
		
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean modifyTrigger(Authentication auth, long triggerId, IotTriggerDto iotTriggerDto)
	{
		// Exception
		if (iotTriggerDto == null)
			return false;
		
		// Field
//		Member member;
		Optional<NodeTrigger> optNodeTrigger;
		NodeTrigger nodeTrigger;
		HashMap<Long, IotTriggerActionDto> mapAction;
		List<NodeTriggerAction> listAction;
		EnableStatusRule enableStatus;
		
		// Init
		listAction = new ArrayList<NodeTriggerAction>();
		mapAction = iotTriggerDto.getMapAction();
//		member = memberService.getMember(auth.getName());
		optNodeTrigger = nodeTriggerRepo.findById(triggerId);
		
		if (iotTriggerDto.isEnableStatus())
			enableStatus = EnableStatusRule.ENABLE;
		else
			enableStatus = EnableStatusRule.DISABLE;
		
		// Exception
		if (/*member == null || */!optNodeTrigger.isPresent())
			return false;
		
		// Init
		nodeTrigger = optNodeTrigger.get();
		
		// DB - Trigger Action Clear
		nodeTriggerActionRepo.deleteAll(nodeTrigger.getNodeTriggerActions());
		
		// Data Process - Node Trigger
		nodeTrigger.setEnableStatus(enableStatus);
		nodeTrigger.setDescription(iotTriggerDto.getDescription());
		nodeTrigger.setExpression(iotTriggerDto.getExpression());
		
		// DB - Node Trigger Save
		nodeTriggerRepo.save(nodeTrigger);
		
		
		// Data Process - Node Trigger Action
		if (mapAction != null)
		{
			mapAction.forEach((key, value) ->
			{
				// Field
				Optional<NodeAction> optNodeAction;
				NodeTriggerAction nodeTriggerAction;
				
				// Init
				optNodeAction = nodeActionRepo.findById(key);
				
				// Build
				if (optNodeAction.isPresent())
				{
					nodeTriggerAction = NodeTriggerAction.builder()
															.nodeTrigger(nodeTrigger)
															.nodeAction(optNodeAction.get())
																.build();
					if (value.isEventError() && value.isEventOccur() && value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ALL);
					else if (value.isEventOccur() && value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR_AND_RESTORE);
					else if (value.isEventError())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.ERROR);
					else if (value.isEventOccur())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.OCCUR);
					else if (value.isEventRestore())
						nodeTriggerAction.setTriggerStatus(TriggerStatusRule.RESTORE);
					
					// List Add
					listAction.add(nodeTriggerAction);
				}
			});
			nodeTrigger.setNodeTriggerActions(listAction);
		}
		
		// DB - Node Trigger Action Save
		nodeTriggerActionRepo.saveAll(listAction);
		
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean setTriggerEnableStatus(Authentication auth, long triggerId, boolean status)
	{
		// Field
		Optional<NodeTrigger> optNodeTrigger;
		NodeTrigger nodeTrigger;
		
		// Init
		optNodeTrigger = nodeTriggerRepo.findById(triggerId);
		
		// Exception
		if (!optNodeTrigger.isPresent())
			return false;
		
		// Load
		nodeTrigger = optNodeTrigger.get();
		
		// DB - Update
		if (status)
			nodeTrigger.setEnableStatus(EnableStatusRule.ENABLE);
		else
			nodeTrigger.setEnableStatus(EnableStatusRule.DISABLE);
		
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
		for (NodeTriggerAction entity : nodeTrigger.getNodeTriggerActions())
			nodeTriggerActionRepo.delete(entity);
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
			if ((nodeTrigger.getLastStatus() == TriggerLastStatusRule.ERROR && isOccur == null) ||
					(nodeTrigger.getLastStatus() == TriggerLastStatusRule.OCCUR && isOccur) ||
					(nodeTrigger.getLastStatus() == TriggerLastStatusRule.RESTORE && !isOccur))
				continue;
			
			// DB - Update
			if (isOccur == null)
				nodeTrigger.setLastStatus(TriggerLastStatusRule.ERROR); // DB Update - Error
			else if (isOccur)
				nodeTrigger.setLastStatus(TriggerLastStatusRule.OCCUR); // Occur
			else
				nodeTrigger.setLastStatus(TriggerLastStatusRule.RESTORE); // Restore
			
			// Load
			listNodeAction.addAll(iotTriggerParserComp.getTriggerAction(nodeTrigger));
		}
		
		// Exec Action
		for (NodeAction nodeAction : listNodeAction)
			if (!iotActionService.execAction(nodeAction))
				isSuccess = false;
		
		// Return
		return isSuccess;
	}
	
	
}
