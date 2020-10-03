package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeTrigger

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 수정뷰 정보 카드정보 DTO
 */
class TriggerInfoCardDto(entity: NodeTrigger) {
    val triggerId: Long = entity.triggerId
    val enableStatus = entity.enableStatus == EnableStatusRule.ENABLE
    val description: String = entity.description
    val expression: String = entity.expression
}