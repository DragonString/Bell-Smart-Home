package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션 아이템 DTO
 */
data class IotActionItemDto (
    var actionItemId: Long = -1,
    var itemId: Long = -1,
    var itemStatus: Double = -1.0
)