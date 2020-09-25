package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeReservAction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 액션 리포지토리 인터페이스
 */
@Repository
interface NodeReservActionRepo : JpaRepository<NodeReservAction?, Long?> {
    @Transactional
    @Modifying
    @Query("DELETE FROM NodeReservAction nra WHERE nra.nodeAction IN :actions")
    fun deleteAllByNodeAction(@Param("actions") nodeAction: List<NodeAction?>?)
    fun countByNodeActionIn(nodeAction: List<NodeAction?>?): Long
}