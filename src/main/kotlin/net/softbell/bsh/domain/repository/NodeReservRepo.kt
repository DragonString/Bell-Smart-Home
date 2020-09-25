package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeReserv;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 리포지토리 인터페이스
 */
@Repository
public interface NodeReservRepo extends JpaRepository<NodeReserv, Long>
{
	List<NodeReserv> findByMember(Member member);
	List<NodeReserv> findByEnableStatus(EnableStatusRule enableStatus);

	@Transactional
	@Modifying
	@Query("DELETE FROM NodeReserv nr WHERE nr.member = ?1")
	void deleteByMember(Member member);
}
