package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 기록 리포지토리 인터페이스
 */
@Repository
public interface NodeItemHistoryRepo extends JpaRepository<NodeItemHistory, Long>
{
	List<NodeItemHistory> findByNodeItem(NodeItem nodeItem, Pageable page);
	NodeItemHistory findFirstByNodeItemOrderByItemHistoryIdDesc(NodeItem nodeItem);
}
