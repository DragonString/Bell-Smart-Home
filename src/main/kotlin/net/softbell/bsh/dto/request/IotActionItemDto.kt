package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 아이템 DTO
 */
data class IotActionItemDto (
        var actionItemId: Long? = null,
        var itemId: Long? = null,
        var itemStatus: Double? = null
)