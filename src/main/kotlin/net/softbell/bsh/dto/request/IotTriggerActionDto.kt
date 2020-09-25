package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 이벤트 액션 DTO
 */
data class IotTriggerActionDto (
        val eventError: Boolean = false,
        val eventOccur: Boolean = false,
        val eventRestore: Boolean = false
)