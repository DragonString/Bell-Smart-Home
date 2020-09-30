package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 아이템 카드정보 DTO
 */
class NodeManageItemCardDto(entity: NodeItem?) {
    var itemId: Long?
    var alias: String?
    var itemName: String?
    var itemMode: ItemModeRule?
    var itemType: ItemTypeRule?
    var itemCategory: ItemCategoryRule?
    var controlMode: Byte?

    init {
        // Exception
        entity.let {
            // Convert
            itemId = entity!!.itemId
            alias = entity.alias
            itemName = entity.itemName
            itemMode = entity.itemMode
            itemType = entity.itemType
            itemCategory = entity.itemCategory
            controlMode = entity.controlMode
        }
    }
}