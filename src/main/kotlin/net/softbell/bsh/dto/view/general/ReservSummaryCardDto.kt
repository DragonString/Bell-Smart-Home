package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeReserv

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약뷰 카드정보 DTO
 */
class ReservSummaryCardDto(entity: NodeReserv?) {
    var reservId: Long?
    var description: String?
    var enableStatus = false
    var creatorNickname: String?

    init {
        // Exception
        entity.let {
            // Convert
            reservId = entity!!.reservId
            description = entity.description
            if (entity.enableStatus === EnableStatusRule.ENABLE) enableStatus = true else enableStatus = false
            creatorNickname = entity.member!!.nickname
        }
    }
}