package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroupItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 리포지토리 인터페이스
 */
@Repository
open interface NodeRepo : JpaRepository<Node?, Long?> {
    fun findByUid(uid: String?): Node?
    fun findByToken(token: String?): Node?
    fun findByNodeGroupItemsIn(listNodeGroupItem: List<NodeGroupItem?>?): List<Node?>
}