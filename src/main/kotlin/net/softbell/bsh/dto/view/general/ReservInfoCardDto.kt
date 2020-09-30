package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeReserv

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 정보 카드정보 DTO
 */
class ReservInfoCardDto(entity: NodeReserv?) {
    var reservId: Long?
    var enableStatus: Boolean
    var description: String?
    var expression: String?

    init {
        // Exception
        entity.let {
            // Init
            enableStatus = false

            // Convert
            reservId = entity!!.reservId
            if (entity.enableStatus === EnableStatusRule.ENABLE) enableStatus = true
            description = entity.description
            expression = entity.expression
        }
    }
}