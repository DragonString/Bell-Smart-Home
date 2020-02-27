package net.softbell.bsh.dto.view.general;

import java.util.ArrayList;
import java.util.Date;
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
	private Date lastReceive;
	private Long lastReceiveSecond;
	
	public MonitorSummaryCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listItems = new ArrayList<MonitorCardItemDto>();
		
		// Convert
		this.alias = entity.getAlias();
	}
}
