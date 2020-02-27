package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.MemberInterlockToken;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 연동 정보 카드정보 DTO
 */
@Getter
@Setter
public class ActionInterlockCardDto
{
	private String name;
	private String token;
	
	public ActionInterlockCardDto(MemberInterlockToken entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.name = entity.getName();
		this.token = entity.getToken();
	}
}