package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
