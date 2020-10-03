package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션 아이템 DTO
 */
data class IotActionItemDto (
        var actionItemId: Long,
        var itemId: Long,
        var itemStatus: Double
)