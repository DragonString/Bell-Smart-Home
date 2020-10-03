package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 리포지토리 인터페이스
 */
@Repository
interface MemberRepo : JpaRepository<Member, Long> {
    fun findByUserId(userId: String): Member?
}