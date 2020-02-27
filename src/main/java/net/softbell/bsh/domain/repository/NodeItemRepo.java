package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemTypeRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 리포지토리 인터페이스
 */
@Repository
public interface NodeItemRepo extends JpaRepository<NodeItem, Long>
{
	List<NodeItem> findByNodeIn(List<Node> listNode);
	List<NodeItem> findByItemCategory(ItemCategoryRule itemCategory);
	List<NodeItem> findByNodeInAndItemCategory(List<Node> listNode, ItemCategoryRule itemCategory);
	NodeItem findByNodeAndItemIndex(Node node, byte itemIndex);
	List<NodeItem> findByItemType(ItemTypeRule itemType);
}
