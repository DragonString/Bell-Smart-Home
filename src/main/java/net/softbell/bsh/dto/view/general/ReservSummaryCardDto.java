package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.NodeReserv;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약뷰 카드정보 DTO
 */
@Getter
@Setter
public class ReservSummaryCardDto
{
	private Long reservId;
	private String description;
	private boolean enableStatus;
	
	public ReservSummaryCardDto(NodeReserv entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.reservId = entity.getReservId();
		this.description = entity.getDescription();
		
		if (entity.getEnableStatus() == EnableStatusRule.ENABLE)
			this.enableStatus = true;
		else
			this.enableStatus = false;
	}
}