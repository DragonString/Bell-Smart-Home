package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 리포지토리 인터페이스
 */
@Repository
public interface NodeRepo extends JpaRepository<Node, Long>
{
	Node findByUid(String uid);
	Node findByToken(String token);
	List<Node> findByNodeGroupItemsIn(List<NodeGroupItem> listNodeGroupItem);
}
