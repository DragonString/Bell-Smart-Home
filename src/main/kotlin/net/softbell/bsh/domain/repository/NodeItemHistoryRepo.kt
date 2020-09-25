package net.softbell.bsh.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	Page<NodeItemHistory> findByNodeItemOrderByItemHistoryIdDesc(NodeItem nodeItem, Pageable page);
	
	@Query("SELECT AVG(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
	Double avgByNodeItem(NodeItem nodeItem, Date date);
	@Query("SELECT MIN(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
	Double minByNodeItem(NodeItem nodeItem, Date date);
	@Query("SELECT MAX(nih.itemStatus) FROM NodeItemHistory nih WHERE nih.nodeItem = ?1 AND receiveDate BETWEEN ?2 AND NOW() ORDER BY nih.itemHistoryId DESC")
	Double maxByNodeItem(NodeItem nodeItem, Date date);
}
