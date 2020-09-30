package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeTrigger

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거뷰 카드정보 DTO
 */
class TriggerSummaryCardDto(entity: NodeTrigger?) {
    var triggerId: Long?
    var description: String?
    var enableStatus = false
    var creatorNickname: String?

    init {
        // Exception
        entity.let {
            // Convert
            triggerId = entity!!.triggerId
            description = entity.description
            if (entity.enableStatus === EnableStatusRule.ENABLE) enableStatus = true else enableStatus = false
            creatorNickname = entity.member!!.nickname
        }
    }
}