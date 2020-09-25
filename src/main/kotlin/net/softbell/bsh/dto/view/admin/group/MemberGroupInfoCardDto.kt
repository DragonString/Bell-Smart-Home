package net.softbell.bsh.dto.view.admin.group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.MemberGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
@Getter
@Setter
public class MemberGroupInfoCardDto
{
	private Long gid;
	private EnableStatusRule enableStatus;
	private String name;
	private List<MemberGroupInfoCardMember> listMembers;
	private List<MemberGroupInfoCardPermission> listPermissions;
	
	public MemberGroupInfoCardDto(MemberGroup entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listMembers = new ArrayList<MemberGroupInfoCardMember>();
		listPermissions = new ArrayList<MemberGroupInfoCardPermission>();
		
		// Convert
		this.gid = entity.getMemberGroupId();
		this.enableStatus = entity.getEnableStatus();
		this.name = entity.getName();
		for (MemberGroupItem child : entity.getMemberGroupItems())
			listMembers.add(new MemberGroupInfoCardMember(child.getMember()));
		for (GroupPermission child : entity.getGroupPermissions())
			listPermissions.add(new MemberGroupInfoCardPermission(child));
	}
	
	@Getter
	@Setter
	public class MemberGroupInfoCardMember
	{
		private Long memberId;
		private String userId;
		private String name;
		private String nickname;
		private String email;
		
		public MemberGroupInfoCardMember(Member entity)
		{
			// Convert
			this.memberId = entity.getMemberId();
			this.userId = entity.getUserId();
			this.name = entity.getName();
			this.nickname = entity.getNickname();
			this.email = entity.getEmail();
		}
	}
	
	@Getter
	@Setter
	public class MemberGroupInfoCardPermission
	{
		private Long pid;
		private Long gid;
		private String name;
		private GroupRole permission;
		private Date assignDate;
		
		public MemberGroupInfoCardPermission(GroupPermission entity)
		{
			this.pid = entity.getGroupPermissionId();
			this.gid = entity.getNodeGroup().getNodeGroupId();
			this.name = entity.getNodeGroup().getName();
			this.permission = entity.getGroupPermission();
			this.assignDate = entity.getAssignDate();
		}
	}
}