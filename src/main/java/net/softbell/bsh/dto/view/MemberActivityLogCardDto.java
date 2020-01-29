package net.softbell.bsh.dto.view;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.AuthStatusRule;
import net.softbell.bsh.domain.entity.MemberLoginLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 활동 로그뷰 카드정보 DTO
 */
@Getter
@Setter
public class MemberActivityLogCardDto
{
	private Date requestDate;
	private String ipv4;
	private AuthStatusRule status;
	
	public MemberActivityLogCardDto(MemberLoginLog entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.requestDate = entity.getRequestDate();
		this.ipv4 = entity.getIpv4();
		this.status = entity.getStatus();
	}
}