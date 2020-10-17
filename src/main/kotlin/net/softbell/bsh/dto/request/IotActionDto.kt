package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션정보 DTO
 */
data class IotActionDto (
    var enableStatus: Boolean = false,
    var description: String,
    var mapActionItem: HashMap<Long, IotActionItemDto>? // TODO Not Null 대체방법 강구
)