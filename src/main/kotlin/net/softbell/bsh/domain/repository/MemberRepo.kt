package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.Member;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 리포지토리 인터페이스
 */
@Repository
public interface MemberRepo extends JpaRepository<Member, Long>
{
	Member findByUserId(String userId);
}
