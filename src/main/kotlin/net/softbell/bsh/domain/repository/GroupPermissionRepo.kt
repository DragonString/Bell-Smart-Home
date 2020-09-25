package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.NodeGroup;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 리포지토리 인터페이스
 */
@Repository
public interface GroupPermissionRepo extends JpaRepository<GroupPermission, Long>
{
	List<GroupPermission> findByGroupPermissionAndMemberGroupIn(GroupRole role, List<MemberGroup> listMemberGroup);
	List<GroupPermission> findByGroupPermissionAndNodeGroupIn(GroupRole role, List<NodeGroup> listNodeGroup);
	List<GroupPermission> findByGroupPermissionAndMemberGroupInAndNodeGroupIn(GroupRole role, List<MemberGroup> listMemberGroup, List<NodeGroup> listNodeGroup);
}
