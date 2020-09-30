package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 수정뷰 아이템 카드정보 DTO
 */
class NodeItemCardDto(entity: NodeItem?, lastHistory: NodeItemHistory) {
    var itemId: Long?
    var alias: String?
    var itemName: String?
    var itemMode: ItemModeRule?
    var itemType: ItemTypeRule?
    var itemCategory: ItemCategoryRule?
    var controlMode: Byte?
    var lastStatus: Double?
    var lastReceive: Date?

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
            lastStatus = lastHistory.itemStatus
            lastReceive = lastHistory.receiveDate
        }
    }
}