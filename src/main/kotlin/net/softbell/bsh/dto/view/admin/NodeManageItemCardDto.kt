package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 관리 수정뷰 아이템 카드정보 DTO
 */
class NodeManageItemCardDto(entity: NodeItem) {
    val itemId: Long = entity.itemId
    val alias: String = entity.alias
    val itemName: String = entity.itemName
    val itemMode: ItemModeRule = entity.itemMode
    val itemType: ItemTypeRule = entity.itemType
    val itemCategory: ItemCategoryRule = entity.itemCategory
    val controlMode: Byte = entity.controlMode
}