package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.NodeAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 리포지토리 인터페이스
 */
@Repository
public interface NodeActionRepo extends JpaRepository<NodeAction, Long>
{
	List<NodeAction> findByMember(Member member);

	@Transactional
	@Modifying
	@Query("DELETE FROM NodeAction na WHERE na.member = ?1")
	void deleteByMember(Member member);
}
