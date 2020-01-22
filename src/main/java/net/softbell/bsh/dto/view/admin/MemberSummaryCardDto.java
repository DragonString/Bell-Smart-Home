package net.softbell.bsh.dto.view.admin;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.BanRule;
import net.softbell.bsh.domain.MemberRole;
import net.softbell.bsh.domain.entity.Member;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 관리뷰 요약 카드정보 DTO
 */
@Getter
@Setter
public class MemberSummaryCardDto
{
	private long memberId;
	private String userId;
	private String username;
	private Date registerDate;
	private BanRule ban;
	private MemberRole permission;
	
	public MemberSummaryCardDto(Member entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.memberId = entity.getMemberId();
		this.userId = entity.getUserId();
		this.username = entity.getUsername();
		this.registerDate = entity.getRegisterDate();
		this.ban = entity.getBan();
		this.permission = entity.getPermission();
	}
}