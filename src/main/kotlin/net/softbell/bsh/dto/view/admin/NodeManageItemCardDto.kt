package net.softbell.bsh.dto.view.admin

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 아이템 카드정보 DTO
 */
@Getter
@Setter
class NodeManageItemCardDto(entity: NodeItem?) {
    private val itemId: Long
    private val alias: String
    private val itemName: String
    private val itemMode: ItemModeRule
    private val itemType: ItemTypeRule
    private val itemCategory: ItemCategoryRule
    private val controlMode: Byte

    init {
        // Exception
        if (entity == null) return

        // Convert
        itemId = entity.getItemId()
        alias = entity.getAlias()
        itemName = entity.getItemName()
        itemMode = entity.getItemMode()
        itemType = entity.getItemType()
        itemCategory = entity.getItemCategory()
        controlMode = entity.getControlMode()
    }
}