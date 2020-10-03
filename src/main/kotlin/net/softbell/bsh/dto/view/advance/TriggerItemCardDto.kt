package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 등록 및 수정뷰 노드 아이템 카드정보 DTO
 */
class TriggerItemCardDto(entity: NodeItem) {
    val nodeId: Long = entity.node.nodeId
    val nodeAlias: String = entity.node.alias
    val itemId: Long = entity.itemId
    val alias: String = entity.alias
    val itemMode: ItemModeRule = entity.itemMode
    val itemType: ItemTypeRule = entity.itemType
    val itemCategory: ItemCategoryRule = entity.itemCategory
}