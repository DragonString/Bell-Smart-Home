package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeTrigger

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 수정뷰 정보 카드정보 DTO
 */
class TriggerInfoCardDto(entity: NodeTrigger?) {
    var triggerId: Long?
    var enableStatus = false
    var description: String?
    var expression: String?

    init {
        // Exception
        entity.let {
            // Convert
            triggerId = entity!!.triggerId
            description = entity.description
            expression = entity.expression
            if (entity.enableStatus === EnableStatusRule.ENABLE) enableStatus = true
        }
    }
}