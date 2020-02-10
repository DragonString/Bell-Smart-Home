package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeTriggerOccurAction;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 발생 액션 리포지토리 인터페이스
 */
@Repository
public interface NodeTriggerOccurActionRepo extends JpaRepository<NodeTriggerOccurAction, Long>
{
	
}
