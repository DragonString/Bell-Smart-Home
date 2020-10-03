package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션뷰 카드정보 DTO
 */
class ActionSummaryCardDto(entity: NodeAction) {
    val actionId: Long = entity.actionId
    val description: String = entity.description
    val creatorNickname: String? = entity.member?.nickname
}