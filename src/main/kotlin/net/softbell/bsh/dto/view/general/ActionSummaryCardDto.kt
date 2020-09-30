package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션뷰 카드정보 DTO
 */
class ActionSummaryCardDto(entity: NodeAction?) {
    var actionId: Long?
    var description: String?
    var creatorNickname: String?

    init {
        // Exception
        entity.let {
            // Convert
            actionId = entity!!.actionId
            description = entity.description
            creatorNickname = entity.member!!.nickname
        }
    }
}