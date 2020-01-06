package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.entity.NodeItemHistoryPK;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 기록 리포지토리 인터페이스
 */
@Repository
public interface NodeItemHistoryRepo extends JpaRepository<NodeItemHistory, NodeItemHistoryPK>
{
	
}
