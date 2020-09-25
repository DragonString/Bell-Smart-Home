package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거정보 DTO
 */
data class IotTriggerDto (
        val enableStatus: Boolean = false,
        val description: String? = null,
        val expression: String? = null,
        val mapAction: HashMap<Long, IotTriggerActionDto>? = null
)