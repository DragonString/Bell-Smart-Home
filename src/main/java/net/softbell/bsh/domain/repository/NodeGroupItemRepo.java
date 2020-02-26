package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 아이템 리포지토리 인터페이스
 */
@Repository
public interface NodeGroupItemRepo extends JpaRepository<NodeGroupItem, Long>
{
	List<NodeGroupItem> findByNode(Node node);
}
