package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 리포지토리 인터페이스
 */
@Repository
open interface NodeItemRepo : JpaRepository<NodeItem?, Long?> {
    fun findByNodeIn(listNode: List<Node?>?): List<NodeItem?>
    fun findByItemCategory(itemCategory: ItemCategoryRule?): List<NodeItem?>
    fun findByNodeInAndItemCategory(listNode: List<Node?>?, itemCategory: ItemCategoryRule?): List<NodeItem?>
    fun findByNodeAndItemIndex(node: Node?, itemIndex: Byte): NodeItem?
    fun findByItemType(itemType: ItemTypeRule?): List<NodeItem?>
}