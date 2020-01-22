package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션뷰 카드정보 DTO
 */
@Getter
@Setter
public class ActionSummaryCardDto
{
	private long actionId;
	private String description;
	
	public ActionSummaryCardDto(NodeAction entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.actionId = entity.getActionId();
		this.description = entity.getDescription();
	}
}