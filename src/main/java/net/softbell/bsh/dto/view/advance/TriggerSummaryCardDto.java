package net.softbell.bsh.dto.view.advance;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
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
	private boolean enableStatus;
	private String creatorNickname;
	
	public TriggerSummaryCardDto(NodeTrigger entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.triggerId = entity.getTriggerId();
		this.description = entity.getDescription();
		
		if (entity.getEnableStatus() == EnableStatusRule.ENABLE)
			this.enableStatus = true;
		else
			this.enableStatus = false;
		this.creatorNickname = entity.getMember().getNickname();
	}
}