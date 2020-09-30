package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거정보 DTO
 */
data class IotTriggerDto (
        var enableStatus: Boolean = false,
        var description: String? = null,
        var expression: String? = null,
        var mapAction: HashMap<Long, IotTriggerActionDto>? = null
)