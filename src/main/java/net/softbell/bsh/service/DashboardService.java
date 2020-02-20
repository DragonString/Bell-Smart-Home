package net.softbell.bsh.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.ItemTypeRule;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.dto.view.DashboardHumidityCardDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 응답 메시지 처리 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DashboardService
{
	// Global Field
	private final NodeItemRepo nodeItemRepo;
	private final NodeItemHistoryRepo nodeItemHistoryRepo;
	
	public List<DashboardHumidityCardDto> getHumidityAlert()
	{
		// Field
		List<DashboardHumidityCardDto> listHumidityCardDto;
		List<NodeItem> listNodeItem;
		
		// Init
		listHumidityCardDto = new ArrayList<DashboardHumidityCardDto>();
		listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_HUMIDITY);
		
		// Exception
		if (listNodeItem.size() <= 0)
			return null;
		
		// Process
		for (NodeItem nodeItem : listNodeItem)
		{
			// Field
			Double avgStatus;
			Calendar calendar = Calendar.getInstance();
	    	calendar.add(Calendar.MINUTE, -5); // 5분 평균값 기준
			
			// Init
	    	long beforeTime = System.currentTimeMillis();
			avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.getTime());
			long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
			long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
			log.info(nodeItem.getAlias() + "평균 로드 끝 (" + secDiffTime + "ms)");
			
			// Process
			if (avgStatus != null && !(avgStatus > 40 && avgStatus <= 60))
				listHumidityCardDto.add(DashboardHumidityCardDto.builder().alias(nodeItem.getNode().getAlias()).avgStatus(avgStatus).build());
		}
		
		// Return
		return listHumidityCardDto;
	}
}
