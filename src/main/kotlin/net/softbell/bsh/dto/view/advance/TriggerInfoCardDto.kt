package net.softbell.bsh.dto.view.advance

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeTrigger
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
class TriggerInfoCardDto(entity: NodeTrigger?) {
    private val triggerId: Long
    private var enableStatus = false
    private val description: String
    private val expression: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        triggerId = entity.getTriggerId()
        description = entity.getDescription()
        expression = entity.getExpression()
        if (entity.getEnableStatus() === EnableStatusRule.ENABLE) enableStatus = true
    }
}