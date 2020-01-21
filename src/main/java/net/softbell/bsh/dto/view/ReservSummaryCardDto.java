package net.softbell.bsh.dto.view;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeReserv;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약뷰 카드정보 DTO
 */
@Getter
@Setter
public class ReservSummaryCardDto
{
	private long reservId;
	private String description;
	
	public ReservSummaryCardDto(NodeReserv entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.reservId = entity.getReservId();
		this.description = entity.getDescription();
	}
}