package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 등록 및 수정뷰 액션 카드정보 DTO
 */
class TriggerActionCardDto(entity: NodeAction?) {
    var actionId: Long?
    var description: String?

    init {
        // Exception
        entity.let {
            // Convert
            actionId = entity!!.actionId
            description = entity.description
        }
    }
}