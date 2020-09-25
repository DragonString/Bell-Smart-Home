package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.domain.entity.NodeGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 리포지토리 인터페이스
 */
@Repository
public interface NodeGroupRepo extends JpaRepository<NodeGroup, Long>
{
	List<NodeGroup> findByEnableStatus(EnableStatusRule enableStatus);
	List<NodeGroup> findByNodeGroupItemsInAndEnableStatus(List<NodeGroupItem> listNodeGroupItem, EnableStatusRule enableStatus);
	List<NodeGroup> findByGroupPermissionsIn(List<GroupPermission> listGroupPermission);
	List<NodeGroup> findByNodeGroupIdInAndGroupPermissionsIn(List<Long> listNodeGroupId, List<GroupPermission> listGroupPermission);
}
