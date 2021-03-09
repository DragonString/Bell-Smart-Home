package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeReserv

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예약뷰 카드정보 DTO
 */
class ReservSummaryCardDto(entity: NodeReserv) {
    val reservId: Long = entity.reservId
    val description: String = entity.description
    val enableStatus: Boolean = entity.enableStatus == EnableStatusRule.ENABLE
    val creatorNickname: String = entity.member.nickname
    val expression: String = entity.expression
}