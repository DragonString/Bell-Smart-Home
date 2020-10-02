package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 아이템 DTO
 */
data class IotActionItemDto (
        var actionItemId: Long,
        var itemId: Long,
        var itemStatus: Double
)