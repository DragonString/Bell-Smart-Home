package net.softbell.bsh.dto.view;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.NodeReserv;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
public class ReservInfoCardDto
{
	private long reservId;
	private boolean enableStatus;
	private String description;
	private String expression;
	
	public ReservInfoCardDto(NodeReserv entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		this.enableStatus = false;
		
		// Convert
		this.reservId = entity.getReservId();
		if (entity.getEnableStatus() == EnableStatusRule.ENABLE)
			this.enableStatus = true;
		this.description = entity.getDescription();
		this.expression = entity.getExpression();
	}
}