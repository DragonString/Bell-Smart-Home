package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.Member;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 회원 카드 아이템 DTO
 */
@Getter
@Setter
public class GroupMemberCardItemDto
{
	private Long memberId;
	private String userId;
	private String name;
	private String nickname;
	private String email;
	
	public GroupMemberCardItemDto(Member entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.memberId = entity.getMemberId();
		this.userId = entity.getUserId();
		this.name = entity.getName();
		this.nickname = entity.getNickname();
		this.email = entity.getEmail();
	}
}