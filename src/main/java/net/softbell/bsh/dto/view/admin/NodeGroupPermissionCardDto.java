package net.softbell.bsh.dto.view.admin;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.MemberGroup;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
@Getter
@Setter
public class NodeGroupPermissionCardDto
{
	private List<NodeGroupNode> listMembers;
	private List<NodeGroupPermission> listPermissions;
	
	public NodeGroupPermissionCardDto(List<MemberGroup> entities)
	{
		// Exception
		if (entities == null || entities.isEmpty())
			return;
		
		// Init
		listMembers = new ArrayList<NodeGroupNode>();
		listPermissions = new ArrayList<NodeGroupPermission>();
		
		// Convert
		for (MemberGroup entity : entities)
			listMembers.add(new NodeGroupNode(entity));
		for (GroupRole role : GroupRole.values())
			listPermissions.add(new NodeGroupPermission(role));
	}
	
	@Getter
	@Setter
	public class NodeGroupNode
	{
		private Long gid;
		private String name;
		
		public NodeGroupNode(MemberGroup entity)
		{
			// Convert
			this.gid = entity.getMemberGroupId();
			this.name = entity.getName();
		}
	}
	
	@Getter
	@Setter
	public class NodeGroupPermission
	{
		private Integer pid;
		private String name;
		
		public NodeGroupPermission(GroupRole role)
		{
			this.pid = role.getCode();
			this.name = role.getValue();
		}
	}
}