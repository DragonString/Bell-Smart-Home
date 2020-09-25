package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberInterlockToken;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰 리포지토리 인터페이스
 */
@Repository
public interface MemberInterlockTokenRepo extends JpaRepository<MemberInterlockToken, Long>
{
	List<MemberInterlockToken> findByMember(Member member);
	MemberInterlockToken findByToken(String token);
	MemberInterlockToken findByEnableStatusAndToken(EnableStatusRule enableStatus, String token);

	@Transactional
	@Modifying
	@Query("DELETE FROM MemberInterlockToken mit WHERE mit.member = ?1")
	void deleteByMember(Member member);
}
