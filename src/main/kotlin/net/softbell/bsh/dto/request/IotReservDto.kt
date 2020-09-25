package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약정보 DTO
 */
data class IotReservDto (
        val enableStatus: Boolean = false,
        val description: String? = null,
        val expression: String? = null,
        val mapAction: HashMap<Long, IotActionDto>? = null
)