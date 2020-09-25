package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.TriggerStatusRule;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.domain.entity.NodeTriggerAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 액션 리포지토리 인터페이스
 */
@Repository
public interface NodeTriggerActionRepo extends JpaRepository<NodeTriggerAction, Long>
{
	List<NodeTriggerAction> findByNodeTriggerAndTriggerStatus(NodeTrigger nodeTrigger, TriggerStatusRule triggerStatus);

	@Transactional
	@Modifying
	@Query("DELETE FROM NodeTriggerAction nta WHERE nta.nodeAction IN :actions")
	void deleteAllByNodeAction(@Param("actions") List<NodeAction> nodeAction);
}
