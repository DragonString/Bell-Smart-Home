package net.softbell.bsh.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.MemberGroupItem;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.domain.entity.NodeGroupItem;
import net.softbell.bsh.domain.repository.GroupPermissionRepo;
import net.softbell.bsh.domain.repository.MemberGroupItemRepo;
import net.softbell.bsh.domain.repository.MemberGroupRepo;
import net.softbell.bsh.domain.repository.NodeGroupItemRepo;
import net.softbell.bsh.domain.repository.NodeGroupRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 권한 관련 컴포넌트
 */
@RequiredArgsConstructor
@Component
public class PermissionComp
{
	// Global Field
	private final GroupPermissionRepo groupPermissionRepo;
	private final MemberGroupRepo memberGroupRepo;
	private final MemberGroupItemRepo memberGroupItemRepo;
	private final NodeGroupRepo nodeGroupRepo;
	private final NodeGroupItemRepo nodeGroupItemRepo;
	
	// 활성화된 사용자 그룹 반환
	public List<MemberGroup> getEnableMemberGroup()
	{
		// Field
		List<MemberGroup> listMemberGroup;
		
		// Init
		listMemberGroup = memberGroupRepo.findByEnableStatus(EnableStatusRule.ENABLE);
		
		// Return
		return listMemberGroup;
	}
	
	// 사용자가 포함된 활성화된 사용자 그룹 반환
	public List<MemberGroup> getEnableMemberGroup(Member member)
	{
		// Field
		List<MemberGroupItem> listMemberGroupItem;
		List<MemberGroup> listMemberGroup;
		
		// Init
		listMemberGroupItem = memberGroupItemRepo.findByMember(member);
		listMemberGroup = memberGroupRepo.findByMemberGroupItemsInAndEnableStatus(listMemberGroupItem, EnableStatusRule.ENABLE);
		
		// Return
		return listMemberGroup;
	}
	
	// 권한이 있는 사용자 그룹 반환
	public List<MemberGroup> getPrivilegeMemberGroup(List<GroupPermission> listGroupPermission)
	{
		// Return
		return memberGroupRepo.findByGroupPermissionsIn(listGroupPermission);
	}
	
	// 권한이 있는 사용자 그룹 반환
	public List<MemberGroup> getPrivilegeMemberGroup(List<MemberGroup> listMemberGroup, List<GroupPermission> listGroupPermission)
	{
		// Field
		List<Long> listMemberGroupId;
		
		// Init
		listMemberGroupId = new ArrayList<Long>();
		
		// Load
		for (MemberGroup entity : listMemberGroup)
			listMemberGroupId.add(entity.getMemberGroupId());
		
		// Return
		return memberGroupRepo.findByMemberGroupIdInAndGroupPermissionsIn(listMemberGroupId, listGroupPermission);
	}
	
	// 활성화된 노드 그룹 반환
	public List<NodeGroup> getEnableNodeGroup()
	{
		// Field
		List<NodeGroup> listNodeGroup;
		
		// Init
		listNodeGroup = nodeGroupRepo.findByEnableStatus(EnableStatusRule.ENABLE);
		
		// Return
		return listNodeGroup;
	}
	
	// 노드가 포함된 활성화된 노드 그룹 반환
	public List<NodeGroup> getEnableNodeGroup(Node node)
	{
		// Field
		List<NodeGroupItem> listNodeGroupItem;
		List<NodeGroup> listNodeGroup;
		
		// Init
		listNodeGroupItem = nodeGroupItemRepo.findByNode(node);
		listNodeGroup = nodeGroupRepo.findByNodeGroupItemsInAndEnableStatus(listNodeGroupItem, EnableStatusRule.ENABLE);
		
		// Return
		return listNodeGroup;
	}
	
	// 권한이 있는 사용자 그룹 반환
	public List<NodeGroup> getPrivilegeNodeGroup(List<NodeGroup> listNodeGroup, List<GroupPermission> listGroupPermission)
	{
		// Field
		List<Long> listNodeGroupId;
		
		// Init
		listNodeGroupId = new ArrayList<Long>();
		
		// Load
		for (NodeGroup entity : listNodeGroup)
			listNodeGroupId.add(entity.getNodeGroupId());
		
		// Return
		return nodeGroupRepo.findByNodeGroupIdInAndGroupPermissionsIn(listNodeGroupId, listGroupPermission);
	}
	
	
	// 사용자 그룹으로 특정 권한이 포함된 그룹 권한 반환
	public List<GroupPermission> getMemberGroupPermission(GroupRole role, List<MemberGroup> listMemberGroup)
	{
		// Field
		List<GroupPermission> listGroupPermission;
		
		// Init
		listGroupPermission = groupPermissionRepo.findByGroupPermissionAndMemberGroupIn(role, listMemberGroup);
		
		// Return
		return listGroupPermission;
	}
	
	// 노드 그룹으로 특정 권한이 포함된 그룹 권한 반환
	public List<GroupPermission> getNodeGroupPermission(GroupRole role, List<NodeGroup> listNodeGroup)
	{
		// Field
		List<GroupPermission> listGroupPermission;
		
		// Init
		listGroupPermission = groupPermissionRepo.findByGroupPermissionAndNodeGroupIn(role, listNodeGroup);
		
		// Return
		return listGroupPermission;
	}
	
	// 노드 그룹과 사용자 그룹으로 특정 권한이 포함된 그룹 권한 반환
	public List<GroupPermission> getGroupPermission(GroupRole role, List<MemberGroup> listMemberGroup, List<NodeGroup> listNodeGroup)
	{
		// Field
		List<GroupPermission> listGroupPermission;
		
		// Init
		listGroupPermission = groupPermissionRepo.findByGroupPermissionAndMemberGroupInAndNodeGroupIn(role, listMemberGroup, listNodeGroup);
		
		// Return
		return listGroupPermission;
	}
}
