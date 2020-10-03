package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeReserv
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 예약 리포지토리 인터페이스
 */
@Repository
interface NodeReservRepo : JpaRepository<NodeReserv, Long> {
    fun findByMember(member: Member): List<NodeReserv>
    fun findByEnableStatus(enableStatus: EnableStatusRule): List<NodeReserv>

    @Transactional
    @Modifying
    @Query("DELETE FROM NodeReserv nr WHERE nr.member = ?1")
    fun deleteByMember(member: Member)
}