package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 이벤트 액션 DTO
 */
data class IotTriggerActionDto (
        var eventError: Boolean,
        var eventOccur: Boolean,
        var eventRestore: Boolean
)