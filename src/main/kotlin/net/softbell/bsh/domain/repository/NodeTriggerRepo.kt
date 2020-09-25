package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeTrigger;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 리포지토리 인터페이스
 */
@Repository
public interface NodeTriggerRepo extends JpaRepository<NodeTrigger, Long>
{
	List<NodeTrigger> findByMember(Member member);
	List<NodeTrigger> findByEnableStatusAndExpressionContaining(EnableStatusRule enableStatus, String expression);

	@Transactional
	@Modifying
	@Query("DELETE FROM NodeTrigger nt WHERE nt.member = ?1")
	void deleteByMember(Member member);
}
