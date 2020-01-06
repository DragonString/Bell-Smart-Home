package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.MemberLoginLog;
import net.softbell.bsh.domain.entity.MemberLoginLogPK;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 로그인 로그 리포지토리 인터페이스
 */
@Repository
public interface MemberLoginLogRepo extends JpaRepository<MemberLoginLog, MemberLoginLogPK>
{
	
}
