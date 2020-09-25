package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeActionItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 아이템 리포지토리 인터페이스
 */
@Repository
public interface NodeActionItemRepo extends JpaRepository<NodeActionItem, Long>
{
	@Transactional
	@Modifying
	@Query("DELETE FROM NodeActionItem nai WHERE nai.nodeAction IN :actions")
	void deleteAllByNodeAction(@Param("actions") List<NodeAction> nodeAction);
}
