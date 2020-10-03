package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 등록 및 수정뷰 액션 카드정보 DTO
 */
class TriggerActionCardDto(entity: NodeAction) {
    val actionId: Long = entity.actionId
    val description: String = entity.description
}