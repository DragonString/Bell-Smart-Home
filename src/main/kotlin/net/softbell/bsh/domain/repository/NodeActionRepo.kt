package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 리포지토리 인터페이스
 */
@Repository
open interface NodeActionRepo : JpaRepository<NodeAction?, Long?> {
    fun findByMember(member: Member?): List<NodeAction?>

    @Transactional
    @Modifying
    @Query("DELETE FROM NodeAction na WHERE na.member = ?1")
    fun deleteByMember(member: Member?)
}