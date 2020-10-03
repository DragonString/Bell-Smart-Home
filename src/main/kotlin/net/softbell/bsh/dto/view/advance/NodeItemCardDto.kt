package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 수정뷰 아이템 카드정보 DTO
 */
class NodeItemCardDto(nodeItem: NodeItem, lastHistory: NodeItemHistory) {
    val itemId: Long = nodeItem.itemId
    val alias: String = nodeItem.alias
    val itemName: String = nodeItem.itemName
    val itemMode: ItemModeRule = nodeItem.itemMode
    val itemType: ItemTypeRule = nodeItem.itemType
    val itemCategory: ItemCategoryRule = nodeItem.itemCategory
    val controlMode: Byte = nodeItem.controlMode
    val lastStatus: Double = lastHistory.itemStatus
    val lastReceive: Date = lastHistory.receiveDate
}