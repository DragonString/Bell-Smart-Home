package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거정보 DTO
 */
data class IotTriggerDto (
        var enableStatus: Boolean = false,
        var description: String,
        var expression: String,
        var mapAction: HashMap<Long, IotTriggerActionDto>? // TODO Not Null 대체방법 강구
)