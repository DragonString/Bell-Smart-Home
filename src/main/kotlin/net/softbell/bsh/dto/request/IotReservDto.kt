package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약정보 DTO
 */
data class IotReservDto (
        var enableStatus: Boolean = false,
        var description: String? = null,
        var expression: String? = null,
        var mapAction: HashMap<Long, IotActionDto>? = null
)