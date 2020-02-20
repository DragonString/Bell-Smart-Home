package net.softbell.bsh.dto.view.general;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드정보 DTO
 */
@Getter
@Setter
public class MonitorSummaryCardDto
{
	private String alias;
	private List<MonitorCardItemDto> listItems;
	private Byte controlMode;
	// TODO 마지막 연결 시각
	
	public MonitorSummaryCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listItems = new ArrayList<MonitorCardItemDto>();
		
		// Convert
		this.alias = entity.getAlias();
		this.controlMode = entity.getControlMode();
	}
}
