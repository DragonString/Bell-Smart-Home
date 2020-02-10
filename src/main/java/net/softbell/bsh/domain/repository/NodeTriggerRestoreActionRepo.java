package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeTriggerRestoreAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 복구 액션 리포지토리 인터페이스
 */
@Repository
public interface NodeTriggerRestoreActionRepo extends JpaRepository<NodeTriggerRestoreAction, Long>
{
	
}
