package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션정보 DTO
 */
data class IotActionDto (
        var actionId: Long? = null,
        var enableStatus: Boolean = false,
        var description: String? = null,
        var mapActionItem: HashMap<Long, IotActionItemDto>? = null
)