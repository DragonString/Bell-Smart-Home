package net.softbell.bsh.dto.view.advance;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeTrigger;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거뷰 카드정보 DTO
 */
@Getter
@Setter
public class TriggerSummaryCardDto
{
	private Long triggerId;
	private String description;
	
	public TriggerSummaryCardDto(NodeTrigger entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.triggerId = entity.getTriggerId();
		this.description = entity.getDescription();
	}
}