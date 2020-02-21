package net.softbell.bsh.dto.view.advance;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.NodeTrigger;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
public class TriggerInfoCardDto
{
	private Long triggerId;
	private boolean enableStatus;
	private String description;
	private String expression;
	
	public TriggerInfoCardDto(NodeTrigger entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.triggerId = entity.getTriggerId();
		this.description = entity.getDescription();
		this.expression = entity.getExpression();
		if (entity.getEnableStatus() == EnableStatusRule.ENABLE)
			enableStatus = true;
	}
}