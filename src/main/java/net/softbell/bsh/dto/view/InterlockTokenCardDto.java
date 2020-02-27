package net.softbell.bsh.dto.view;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.MemberInterlockToken;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰뷰 카드정보 DTO
 */
@Getter
@Setter
public class InterlockTokenCardDto
{
	private Long tokenId;
	private String name;
	private String token;
	private EnableStatusRule enableStatus;
	private Date registerDate;
	
	public InterlockTokenCardDto(MemberInterlockToken entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.tokenId = entity.getMemberInterlockId();
		this.name = entity.getName();
		this.token = entity.getToken();
		this.enableStatus = entity.getEnableStatus();
		this.registerDate = entity.getRegisterDate();
	}
}