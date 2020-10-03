package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeActionItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 액션 아이템 리포지토리 인터페이스
 */
@Repository
interface NodeActionItemRepo : JpaRepository<NodeActionItem, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM NodeActionItem nai WHERE nai.nodeAction IN :actions")
    fun deleteAllByNodeAction(@Param("actions") nodeAction: List<NodeAction>)
}