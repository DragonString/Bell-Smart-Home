package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeTrigger
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 리포지토리 인터페이스
 */
@Repository
interface NodeTriggerRepo : JpaRepository<NodeTrigger, Long> {
    fun findByMember(member: Member?): List<NodeTrigger?>?
    fun findByEnableStatusAndExpressionContaining(enableStatus: EnableStatusRule?, expression: String?): List<NodeTrigger?>?

    @Transactional
    @Modifying
    @Query("DELETE FROM NodeTrigger nt WHERE nt.member = ?1")
    fun deleteByMember(member: Member?)
}