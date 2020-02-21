package net.softbell.bsh.iot.component.v1;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeReserv;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 예약 스케줄러 v1
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IotReservSchedulerV1
{
	// Global Field
	private final IotReservParserV1 iotReservParser;
	private final IotActionServiceV1 iotActionService;
	
	@Scheduled(cron = "0 * * * * *")
	@Transactional
	public void iotReservTask()
	{
		// Field
		List<NodeReserv> listNodeReserv;
		
		// Init
		listNodeReserv = iotReservParser.getEnableReserv();
		
		// Process
		for (NodeReserv nodeReserv : listNodeReserv)
		{
			// Field
			Boolean isSuccess;
			
			// Parse
			isSuccess = iotReservParser.parseEntity(nodeReserv);
			
			// Process
			if (isSuccess == true)
			{
				log.info("예약 실행 (" + nodeReserv.getDescription() + ")");
				for (NodeAction nodeAction : iotReservParser.getReservAction(nodeReserv)) // Get Reserv Action
					iotActionService.execAction(nodeAction); // Exec Action
			}
		}
	}
}
