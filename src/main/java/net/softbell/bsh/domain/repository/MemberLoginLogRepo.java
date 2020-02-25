package net.softbell.bsh.domain.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.AuthStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberLoginLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 로그인 로그 리포지토리 인터페이스
 */
@Repository
public interface MemberLoginLogRepo extends JpaRepository<MemberLoginLog, Long>
{
	Page<MemberLoginLog> findByMember(Member member, Pageable pageable);
	long countByMember(Member member);
	long countByMemberAndStatusAndRequestDateBetween(Member member, AuthStatusRule status, Date start, Date end);
}
