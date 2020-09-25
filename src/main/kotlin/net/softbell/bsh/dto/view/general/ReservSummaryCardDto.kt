package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeReserv

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약뷰 카드정보 DTO
 */
class ReservSummaryCardDto(entity: NodeReserv?) {
    private val reservId: Long
    private val description: String
    private var enableStatus = false
    private val creatorNickname: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        reservId = entity.getReservId()
        description = entity.getDescription()
        if (entity.getEnableStatus() === EnableStatusRule.ENABLE) enableStatus = true else enableStatus = false
        creatorNickname = entity.getMember().getNickname()
    }
}