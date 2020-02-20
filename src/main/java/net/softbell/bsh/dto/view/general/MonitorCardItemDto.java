package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드 아이템 정보 DTO
 */
@Getter
@Setter
public class MonitorCardItemDto
{
	private String alias;
	private Double lastStatus;
	private Integer widthPercent;
	
	public MonitorCardItemDto(NodeItem entity, NodeItemHistory lastHistory)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.alias = entity.getAlias();
		this.lastStatus = lastHistory.getItemStatus();
		switch (entity.getItemMode())
		{
			case DIGITAL:
				if (this.lastStatus == 0)
					this.widthPercent = 0;
				else
					this.widthPercent = 100;
				
				break;
				
			case ANALOG:
				this.widthPercent = (int) (this.lastStatus / 10);
					
				break;
				
			default:
				this.widthPercent = 0;
				
				break;
		}
	}
}