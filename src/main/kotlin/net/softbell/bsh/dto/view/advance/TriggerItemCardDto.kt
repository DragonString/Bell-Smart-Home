package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 등록 및 수정뷰 노드 아이템 카드정보 DTO
 */
class TriggerItemCardDto(entity: NodeItem?) {
    private val nodeId: Long
    private val nodeAlias: String
    private val itemId: Long
    private val alias: String
    private val itemMode: ItemModeRule
    private val itemType: ItemTypeRule
    private val itemCategory: ItemCategoryRule

    init {
        // Exception
        if (entity == null) return

        // Field
        val node: Node

        // Init
        node = entity.getNode()

        // Convert
        nodeId = node.getNodeId()
        nodeAlias = node.getAlias()
        itemId = entity.getItemId()
        alias = entity.getAlias()
        itemMode = entity.getItemMode()
        itemType = entity.getItemType()
        itemCategory = entity.getItemCategory()
    }
}