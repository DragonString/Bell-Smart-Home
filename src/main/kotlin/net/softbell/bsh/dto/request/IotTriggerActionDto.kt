package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 이벤트 액션 DTO
 */
data class IotTriggerActionDto (
        var eventError: Boolean = false,
        var eventOccur: Boolean = false,
        var eventRestore: Boolean = false
)