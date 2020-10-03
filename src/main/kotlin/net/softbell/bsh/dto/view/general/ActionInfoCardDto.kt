package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeAction

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예약 등록 및 수정뷰 정보 카드정보 DTO
 */
class ActionInfoCardDto(entity: NodeAction) {
    val actionId: Long = entity.actionId
    val enableStatus: Boolean = entity.enableStatus == EnableStatusRule.ENABLE
    val description: String = entity.description
}