package net.softbell.bsh.dto.view.admin.group;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.NodeGroup;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
@Getter
@Setter
public class MemberGroupPermissionCardDto
{
	private List<MemberGroupNode> listNodes;
	private List<MemberGroupPermission> listPermissions;
	
	public MemberGroupPermissionCardDto(List<NodeGroup> entities)
	{
		// Exception
		if (entities == null || entities.isEmpty())
			return;
		
		// Init
		listNodes = new ArrayList<MemberGroupNode>();
		listPermissions = new ArrayList<MemberGroupPermission>();
		
		// Convert
		for (NodeGroup entity : entities)
			listNodes.add(new MemberGroupNode(entity));
		for (GroupRole role : GroupRole.values())
			listPermissions.add(new MemberGroupPermission(role));
	}
	
	@Getter
	@Setter
	public class MemberGroupNode
	{
		private Long gid;
		private String name;
		
		public MemberGroupNode(NodeGroup entity)
		{
			// Convert
			this.gid = entity.getNodeGroupId();
			this.name = entity.getName();
		}
	}
	
	@Getter
	@Setter
	public class MemberGroupPermission
	{
		private Integer pid;
		private String name;
		
		public MemberGroupPermission(GroupRole role)
		{
			this.pid = role.getCode();
			this.name = role.getValue();
		}
	}
}