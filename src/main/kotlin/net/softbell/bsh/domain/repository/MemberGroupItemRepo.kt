package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroupItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 아이템 리포지토리 인터페이스
 */
@Repository
interface MemberGroupItemRepo : JpaRepository<MemberGroupItem, Long> {
    fun findByMember(member: Member?): List<MemberGroupItem?>?

    @Transactional
    @Modifying
    @Query("DELETE FROM MemberGroupItem mgi WHERE mgi.member = ?1")
    fun deleteByMember(member: Member?)
}