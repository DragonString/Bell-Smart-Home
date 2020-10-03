package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberLoginLog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 로그인 로그 리포지토리 인터페이스
 */
@Repository
interface MemberLoginLogRepo : JpaRepository<MemberLoginLog, Long> {
    fun findByMember(member: Member, pageable: Pageable): Page<MemberLoginLog>
    fun countByMember(member: Member): Long
    fun countByMemberAndStatusAndRequestDateBetween(member: Member, status: AuthStatusRule, start: Date, end: Date): Long

    @Transactional
    @Modifying
    @Query("DELETE FROM MemberLoginLog mll WHERE mll.member = ?1")
    fun deleteByMember(member: Member)
}