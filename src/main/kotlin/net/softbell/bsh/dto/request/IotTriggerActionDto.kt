package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 이벤트 액션 DTO
 */
data class IotTriggerActionDto (
        var eventError: Boolean = false,
        var eventOccur: Boolean = false,
        var eventRestore: Boolean = false
)