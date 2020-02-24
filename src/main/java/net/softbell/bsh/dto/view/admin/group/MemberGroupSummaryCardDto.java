package net.softbell.bsh.dto.view.admin.group;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.MemberGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹뷰 요약 카드정보 DTO
 */
@Getter
@Setter
public class MemberGroupSummaryCardDto
{
	private Long gid;
	private String name;
	private EnableStatusRule enableStatus;
	private List<MemberGroupItemDto> listMember;
	
	public MemberGroupSummaryCardDto(MemberGroup entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listMember = new ArrayList<MemberGroupItemDto>();
		
		// Convert
		this.gid = entity.getMemberGroupId();
		this.name = entity.getName();
		this.enableStatus = entity.getEnableStatus();
		for (MemberGroupItem child : entity.getMemberGroupItems())
			listMember.add(new MemberGroupItemDto(child.getMember()));
	}
	
	@Getter
	@Setter
	public class MemberGroupItemDto
	{
		private Long memberId;
		private String nickname;
		
		public MemberGroupItemDto(Member entity)
		{
			this.memberId = entity.getMemberId();
			this.nickname = entity.getNickname();
		}
	}
}