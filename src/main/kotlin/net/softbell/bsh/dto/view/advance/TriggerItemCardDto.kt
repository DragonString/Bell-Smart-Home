package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 등록 및 수정뷰 노드 아이템 카드정보 DTO
 */
class TriggerItemCardDto(entity: NodeItem?) {
    var nodeId: Long?
    var nodeAlias: String?
    var itemId: Long?
    var alias: String?
    var itemMode: ItemModeRule?
    var itemType: ItemTypeRule?
    var itemCategory: ItemCategoryRule?

    init {
        // Exception
        entity.let {
            // Field
            val node: Node?

            // Init
            node = entity!!.node

            // Convert
            nodeId = node!!.nodeId
            nodeAlias = node.alias
            itemId = entity.itemId
            alias = entity.alias
            itemMode = entity.itemMode
            itemType = entity.itemType
            itemCategory = entity.itemCategory
        }
    }
}