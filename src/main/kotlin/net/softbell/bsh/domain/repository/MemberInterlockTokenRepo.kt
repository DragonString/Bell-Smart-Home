package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberInterlockToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰 리포지토리 인터페이스
 */
@Repository
interface MemberInterlockTokenRepo : JpaRepository<MemberInterlockToken, Long> {
    fun findByMember(member: Member?): List<MemberInterlockToken?>?
    fun findByToken(token: String?): MemberInterlockToken?
    fun findByEnableStatusAndToken(enableStatus: EnableStatusRule?, token: String?): MemberInterlockToken?

    @Transactional
    @Modifying
    @Query("DELETE FROM MemberInterlockToken mit WHERE mit.member = ?1")
    fun deleteByMember(member: Member?)
}