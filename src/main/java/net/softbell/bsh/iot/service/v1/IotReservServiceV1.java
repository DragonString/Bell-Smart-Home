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
import net.softbell.bsh.domain.entity.NodeReserv;
import net.softbell.bsh.domain.entity.NodeReservAction;
import net.softbell.bsh.domain.repository.NodeActionRepo;
import net.softbell.bsh.domain.repository.NodeReservActionRepo;
import net.softbell.bsh.domain.repository.NodeReservRepo;
import net.softbell.bsh.dto.request.IotActionDto;
import net.softbell.bsh.dto.request.IotReservDto;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Reservation 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotReservServiceV1
{
	// Global Field
	private final MemberService memberService;
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	private final NodeReservRepo nodeReservRepo;
	private final NodeReservActionRepo nodeReservActionRepo;
	private final NodeActionRepo nodeActionRepo;

	public List<NodeAction> getAvailableAction(Authentication auth)
	{
		// Field
		List<NodeAction> listNodeAction;
		
		// TODO 계정에서 접근가능한 아이템만 반환하도록 추가해야됨.. 나중에... 언젠가는 추가하겠지...?
		// Init
		listNodeAction = nodeActionRepo.findAll();
		
		// Return
		return listNodeAction;
	}
	
	public List<NodeReserv> getAllReservs(Authentication auth)
	{
		// Field
		return nodeReservRepo.findAll();
	}
	
	public NodeReserv getReserv(Authentication auth, long reservId)
	{
		// Field
		Optional<NodeReserv> optNodeReserv;
		NodeReserv nodeReserv;
		
		// Init
		optNodeReserv = nodeReservRepo.findById(reservId);
		
		// Exception
		if (!optNodeReserv.isPresent())
			return null;
		
		// Load
		nodeReserv = optNodeReserv.get();
		
		// Return
		return nodeReserv;
	}
	
	@Transactional
	public boolean createReservation(Authentication auth, IotReservDto iotReservDto)
	{
		// Log
		log.info(BellLog.getLogHead() + "Creating Reservation (" + iotReservDto.getDescription() + ")");
		
		// Field
		Member member;
		NodeReserv nodeReserv;
		HashMap<Long, IotActionDto> mapAction;
		List<NodeReservAction> listNodeReservAction;
		EnableStatusRule enableStatus;
		
		// Init
		member = memberService.getMember(auth.getName());
		listNodeReservAction = new ArrayList<NodeReservAction>();
		mapAction = iotReservDto.getMapAction();
		if (iotReservDto.isEnableStatus())
			enableStatus = EnableStatusRule.ENABLE;
		else
			enableStatus = EnableStatusRule.DISABLE;
		
		// Exception
		if (member == null)
			return false;
		
		// Data Process - Reservation Info
		nodeReserv = NodeReserv.builder()
								.enableStatus(enableStatus)
								.description(iotReservDto.getDescription())
								.expression(iotReservDto.getExpression())
								.member(member)
									.build();

		// Data Process - Reservation Action Info
		if (mapAction != null)
		{
			mapAction.forEach((key, value) ->
			{
				if(value.getActionId() != 0)
				{
					// Field
					Optional<NodeAction> optNodeAction;
					NodeReservAction nodeReservAction;
					
					// Init
					optNodeAction = nodeActionRepo.findById(value.getActionId());
					
					// Build
					if (optNodeAction.isPresent())
					{
						nodeReservAction = NodeReservAction.builder()
														.nodeAction(optNodeAction.get())
														.nodeReserv(nodeReserv)
															.build();
						
						// List Add
						listNodeReservAction.add(nodeReservAction);
					}
				}
			});
			nodeReserv.setNodeReservActions(listNodeReservAction);
		}

		// DB - Save
		nodeReservRepo.save(nodeReserv);
		nodeReservActionRepo.saveAll(listNodeReservAction);
		
		// Log
		log.info(BellLog.getLogHead() + "Created Reservation (" + nodeReserv.getReservId() + ", " + iotReservDto.getDescription() + ")");
		
		// Return
		return true;
	}
}
