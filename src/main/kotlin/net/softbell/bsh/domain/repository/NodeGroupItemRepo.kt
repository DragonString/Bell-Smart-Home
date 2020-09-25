package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroupItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 아이템 리포지토리 인터페이스
 */
@Repository
interface NodeGroupItemRepo : JpaRepository<NodeGroupItem?, Long?> {
    fun findByNode(node: Node?): List<NodeGroupItem?>?
}