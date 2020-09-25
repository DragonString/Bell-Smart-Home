package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션정보 DTO
 */
data class IotActionDto (
        val actionId: Long? = null,
        val enableStatus: Boolean = false,
        val description: String? = null,
        val mapActionItem: HashMap<Long, IotActionItemDto>? = null
)