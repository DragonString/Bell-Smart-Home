package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.TriggerStatusRule
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeTrigger
import net.softbell.bsh.domain.entity.NodeTriggerAction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 트리거 액션 리포지토리 인터페이스
 */
@Repository
interface NodeTriggerActionRepo : JpaRepository<NodeTriggerAction, Long> {
    fun findByNodeTriggerAndTriggerStatus(nodeTrigger: NodeTrigger, triggerStatus: TriggerStatusRule): List<NodeTriggerAction>

    @Transactional
    @Modifying
    @Query("DELETE FROM NodeTriggerAction nta WHERE nta.nodeAction IN :actions")
    fun deleteAllByNodeAction(@Param("actions") nodeAction: List<NodeAction>)
}