package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.entity.NodeTrigger

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거뷰 카드정보 DTO
 */
class TriggerSummaryCardDto(entity: NodeTrigger?) {
    private val triggerId: Long
    private val description: String
    private var enableStatus = false
    private val creatorNickname: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        triggerId = entity.getTriggerId()
        description = entity.getDescription()
        if (entity.getEnableStatus() === EnableStatusRule.ENABLE) enableStatus = true else enableStatus = false
        creatorNickname = entity.getMember().getNickname()
    }
}