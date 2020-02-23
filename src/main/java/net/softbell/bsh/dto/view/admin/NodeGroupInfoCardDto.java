package net.softbell.bsh.dto.view.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.domain.entity.NodeGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
@Getter
@Setter
public class NodeGroupInfoCardDto
{
	private Long gid;
	private EnableStatusRule enableStatus;
	private String name;
	private List<MemberGroupInfoCardNode> listNodes;
	private List<MemberGroupInfoCardPermission> listPermissions;
	
	public NodeGroupInfoCardDto(NodeGroup entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listNodes = new ArrayList<MemberGroupInfoCardNode>();
		listPermissions = new ArrayList<MemberGroupInfoCardPermission>();
		
		// Convert
		this.gid = entity.getNodeGroupId();
		this.enableStatus = entity.getEnableStatus();
		this.name = entity.getName();
		for (NodeGroupItem child : entity.getNodeGroupItems())
			listNodes.add(new MemberGroupInfoCardNode(child.getNode()));
		for (GroupPermission child : entity.getGroupPermissions())
			listPermissions.add(new MemberGroupInfoCardPermission(child));
	}
	
	@Getter
	@Setter
	public class MemberGroupInfoCardNode
	{
		private Long nodeId;
		private String uid;
		private String name;
		private String alias;
		
		public MemberGroupInfoCardNode(Node entity)
		{
			// Convert
			this.nodeId = entity.getNodeId();
			this.uid = entity.getUid();
			this.name = entity.getNodeName();
			this.alias = entity.getAlias();
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
			this.gid = entity.getMemberGroup().getMemberGroupId();
			this.name = entity.getMemberGroup().getName();
			this.permission = entity.getGroupPermission();
			this.assignDate = entity.getAssignDate();
		}
	}
}