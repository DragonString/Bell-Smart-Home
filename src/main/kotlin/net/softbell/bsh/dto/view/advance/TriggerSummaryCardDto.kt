package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeTrigger

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거뷰 카드정보 DTO
 */
class TriggerSummaryCardDto(entity: NodeTrigger) {
    val triggerId: Long = entity.triggerId
    val description: String = entity.description
    val enableStatus = entity.enableStatus == EnableStatusRule.ENABLE
    val creatorNickname: String = entity.member.nickname
}