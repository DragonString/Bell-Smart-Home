package net.softbell.bsh.dto.view;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.BanRule;
import net.softbell.bsh.domain.MemberRole;
import net.softbell.bsh.domain.entity.Member;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 프로필뷰 카드정보 DTO
 */
@Getter
@Setter
public class MemberProfileCardDto
{
	private String name;
	private String nickname;
	private String userId;
	private String email;
	private BanRule ban;
	private MemberRole permission;
	private Date registerDate;
	
	public MemberProfileCardDto(Member entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.name = entity.getName();
		this.nickname = entity.getNickname();
		this.userId = entity.getUserId();
		this.email = entity.getEmail();
		this.ban = entity.getBan();
		this.permission = entity.getPermission();
		this.registerDate = entity.getRegisterDate();
	}
}