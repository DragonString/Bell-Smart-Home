package net.softbell.bsh.dto.view;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
@Getter
@Setter
public class ReservActionCardDto
{
	private long actionId;
	private String description;
	
	public ReservActionCardDto(NodeAction entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.actionId = entity.getActionId();
		this.description = entity.getDescription();
	}
}