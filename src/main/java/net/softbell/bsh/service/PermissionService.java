package net.softbell.bsh.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import net.softbell.bsh.dto.request.MemberGroupDto;
import net.softbell.bsh.dto.request.MemberGroupPermissionDto;
import net.softbell.bsh.dto.request.NodeGroupDto;
import net.softbell.bsh.dto.request.NodeGroupPermissionDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 권한 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class PermissionService
{
	// Global Field
	private final MemberService memberService;
	private final IotNodeServiceV1 iotNodeService;
	
	private final GroupPermissionRepo groupPermissionRepo;
	private final NodeGroupRepo nodeGroupRepo;
	private final NodeGroupItemRepo nodeGroupItemRepo;
	private final MemberGroupRepo memberGroupRepo;
	private final MemberGroupItemRepo memberGroupItemRepo;
	
	public List<MemberGroup> getAllMemberGroup()
	{
		return memberGroupRepo.findAll();
	}
	
	public List<NodeGroup> getAllNodeGroup()
	{
		return nodeGroupRepo.findAll();
	}
	
	public MemberGroup getMemberGroup(long gid)
	{
		// Field
		Optional<MemberGroup> optMemberGroup;
		
		// Init
		optMemberGroup = memberGroupRepo.findById(gid);
		
		// Exception
		if (!optMemberGroup.isPresent())
			return null;
		
		// Return
		return optMemberGroup.get();
	}
	
	public NodeGroup getNodeGroup(long gid)
	{
		// Field
		Optional<NodeGroup> optNodeGroup;
		
		// Init
		optNodeGroup = nodeGroupRepo.findById(gid);
		
		// Exception
		if (!optNodeGroup.isPresent())
			return null;
		
		// Return
		return optNodeGroup.get();
	}
	
	@Transactional
	public boolean createMemberGroup(MemberGroupDto memberGroupDto)
	{
		// Field
		MemberGroup memberGroup;
		
		// Parent Load
		memberGroup = MemberGroup.builder().name(memberGroupDto.getName()).build();
		if (memberGroupDto.isEnableStatus())
			memberGroup.setEnableStatus(EnableStatusRule.ENABLE);
		else
			memberGroup.setEnableStatus(EnableStatusRule.DISABLE);
		
		// DB - Save
		memberGroupRepo.save(memberGroup);
		
		// Child Load
		for (long memberId : memberGroupDto.getMemberId())
		{
			// Field
			Member member;
			MemberGroupItem memberGroupItem;
			
			// Init
			member = memberService.getMember(memberId);
			
			// Exception
			if (member == null)
				continue;
			
			// Create
			memberGroupItem = MemberGroupItem.builder()
													.memberGroup(memberGroup)
													.member(member)
													.assignDate(new Date())
														.build();
			
			// DB = Save
			memberGroupItemRepo.save(memberGroupItem);
		}
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean enableMemberGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<MemberGroup> optMemberGroup;
			
			// Init
			optMemberGroup = memberGroupRepo.findById(gid);
			
			// Exception
			if (!optMemberGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// DB - Update
			optMemberGroup.get().setEnableStatus(EnableStatusRule.ENABLE);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean disableMemberGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<MemberGroup> optMemberGroup;
			
			// Init
			optMemberGroup = memberGroupRepo.findById(gid);
			
			// Exception
			if (!optMemberGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// DB - Update
			optMemberGroup.get().setEnableStatus(EnableStatusRule.DISABLE);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean deleteMemberGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<MemberGroup> optMemberGroup;
			MemberGroup memberGroup;
			
			// Init
			optMemberGroup = memberGroupRepo.findById(gid);
			
			// Exception
			if (!optMemberGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// Load
			memberGroup = optMemberGroup.get();
			
			// DB - Delete
			groupPermissionRepo.deleteAll(memberGroup.getGroupPermissions());
			memberGroupItemRepo.deleteAll(memberGroup.getMemberGroupItems());
			memberGroupRepo.delete(memberGroup);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean addMemberPermission(Long gid, MemberGroupPermissionDto memberGroupPermissionDto)
	{
		// Field
		MemberGroup memberGroup;
		NodeGroup nodeGroup;
		GroupPermission groupPermission;
		GroupRole groupRole;
		
		// Init
		memberGroup = getMemberGroup(gid);
		nodeGroup = getNodeGroup(memberGroupPermissionDto.getNodeGid());
		groupRole = GroupRole.ofLegacyCode(memberGroupPermissionDto.getPermission());
		
		// Exception
		if (memberGroup == null || nodeGroup == null)
			return false;
		// TODO 기존 권한이 있으면 생성 중단하는 코드 필요
		
		// Make
		groupPermission = GroupPermission.builder()
												.assignDate(new Date())
												.memberGroup(memberGroup)
												.nodeGroup(nodeGroup)
												.groupPermission(groupRole)
													.build();
		
		// DB - Save
		groupPermissionRepo.save(groupPermission);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean createNodeGroup(NodeGroupDto nodeGroupDto)
	{
		// Field
		NodeGroup nodeGroup;
		
		// Parent Load
		nodeGroup = NodeGroup.builder().name(nodeGroupDto.getName()).build();
		if (nodeGroupDto.isEnableStatus())
			nodeGroup.setEnableStatus(EnableStatusRule.ENABLE);
		else
			nodeGroup.setEnableStatus(EnableStatusRule.DISABLE);
		
		// DB - Save
		nodeGroupRepo.save(nodeGroup);
		
		// Child Load
		for (long nodeId : nodeGroupDto.getNodeId())
		{
			// Field
			Node node;
			NodeGroupItem nodeGroupItem;
			
			// Init
			node = iotNodeService.getNode(nodeId);
			
			// Exception
			if (node == null)
				continue;
			
			// Create
			nodeGroupItem = NodeGroupItem.builder()
												.nodeGroup(nodeGroup)
												.node(node)
												.assignDate(new Date())
													.build();
			
			// DB = Save
			nodeGroupItemRepo.save(nodeGroupItem);
		}
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean enableNodeGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<NodeGroup> optNodeGroup;
			
			// Init
			optNodeGroup = nodeGroupRepo.findById(gid);
			
			// Exception
			if (!optNodeGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// DB - Update
			optNodeGroup.get().setEnableStatus(EnableStatusRule.ENABLE);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean disableNodeGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<NodeGroup> optNodeGroup;
			
			// Init
			optNodeGroup = nodeGroupRepo.findById(gid);
			
			// Exception
			if (!optNodeGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// DB - Update
			optNodeGroup.get().setEnableStatus(EnableStatusRule.DISABLE);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean deleteNodeGroup(List<Long> listGid)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = true;
		
		// Process
		for (Long gid : listGid)
		{
			// Field
			Optional<NodeGroup> optNodeGroup;
			NodeGroup nodeGroup;
			
			// Init
			optNodeGroup = nodeGroupRepo.findById(gid);
			
			// Exception
			if (!optNodeGroup.isPresent())
			{
				isSuccess = false;
				continue;
			}
			
			// Load
			nodeGroup = optNodeGroup.get();
			
			// DB - Delete
			groupPermissionRepo.deleteAll(nodeGroup.getGroupPermissions());
			nodeGroupItemRepo.deleteAll(nodeGroup.getNodeGroupItems());
			nodeGroupRepo.delete(nodeGroup);
		}
		
		// Return
		return isSuccess;
	}
	
	@Transactional
	public boolean addNodePermission(Long gid, NodeGroupPermissionDto nodeGroupPermissionDto)
	{
		// Field
		MemberGroup memberGroup;
		NodeGroup nodeGroup;
		GroupPermission groupPermission;
		GroupRole groupRole;
		
		// Init
		memberGroup = getMemberGroup(nodeGroupPermissionDto.getMemberGid());
		nodeGroup = getNodeGroup(gid);
		groupRole = GroupRole.ofLegacyCode(nodeGroupPermissionDto.getPermission());
		
		// Exception
		if (memberGroup == null || nodeGroup == null)
			return false;
		// TODO 기존 권한이 있으면 생성 중단하는 코드 필요
		
		// Make
		groupPermission = GroupPermission.builder()
												.assignDate(new Date())
												.memberGroup(memberGroup)
												.nodeGroup(nodeGroup)
												.groupPermission(groupRole)
													.build();
		
		// DB - Save
		groupPermissionRepo.save(groupPermission);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean deleteGroupPermission(Long gid)
	{
		// Field
		Optional<GroupPermission> optGroupPermission;
		
		// Init
		optGroupPermission = groupPermissionRepo.findById(gid);
		
		// Exception
		if (!optGroupPermission.isPresent())
			return false;
		
		// DB - Save
		groupPermissionRepo.delete(optGroupPermission.get());
		
		// Return
		return true;
	}
}
