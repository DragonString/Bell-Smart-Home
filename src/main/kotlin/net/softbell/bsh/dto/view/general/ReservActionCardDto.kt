package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
class ReservActionCardDto(entity: NodeAction?) {
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