package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeAction

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 정보 카드정보 DTO
 */
class ActionInfoCardDto(entity: NodeAction?) {
    var actionId: Long?
    var enableStatus: Boolean
    var description: String?

    init {
        // Exception
        entity.let {
            // Init
            enableStatus = false

            // Convert
            actionId = entity!!.actionId
            if (entity.enableStatus === EnableStatusRule.ENABLE) enableStatus = true
            description = entity.description
        }
    }
}