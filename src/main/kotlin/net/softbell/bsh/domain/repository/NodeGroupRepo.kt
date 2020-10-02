package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.NodeGroup
import net.softbell.bsh.domain.entity.NodeGroupItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 리포지토리 인터페이스
 */
@Repository
interface NodeGroupRepo : JpaRepository<NodeGroup, Long> {
    fun findByEnableStatus(enableStatus: EnableStatusRule?): List<NodeGroup>?
    fun findByNodeGroupItemsInAndEnableStatus(listNodeGroupItem: List<NodeGroupItem?>?, enableStatus: EnableStatusRule?): List<NodeGroup?>?
    fun findByGroupPermissionsIn(listGroupPermission: List<GroupPermission?>?): List<NodeGroup?>?
    fun findByNodeGroupIdInAndGroupPermissionsIn(listNodeGroupId: List<Long?>?, listGroupPermission: List<GroupPermission?>?): List<NodeGroup?>?
}