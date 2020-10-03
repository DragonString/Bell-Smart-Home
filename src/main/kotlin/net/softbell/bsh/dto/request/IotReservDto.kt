package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예약정보 DTO
 */
data class IotReservDto (
        var enableStatus: Boolean,
        var description: String,
        var expression: String,
        var mapAction: HashMap<Long, IotActionDto>? // TODO Not Null 대체방법 강구
)