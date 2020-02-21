package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 등록 및 수정뷰 액션 카드정보 DTO
 */
@Getter
@Setter
public class TriggerActionCardDto
{
	private Long actionId;
	private String description;
	
	public TriggerActionCardDto(NodeAction entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.actionId = entity.getActionId();
		this.description = entity.getDescription();
	}
}