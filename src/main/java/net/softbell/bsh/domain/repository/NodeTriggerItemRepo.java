package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeTriggerItem;
import net.softbell.bsh.domain.entity.NodeTriggerItemPK;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 아이템 리포지토리 인터페이스
 */
@Repository
public interface NodeTriggerItemRepo extends JpaRepository<NodeTriggerItem, NodeTriggerItemPK>
{
	
}
