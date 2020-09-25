package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeAction

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
class ReservActionCardDto(entity: NodeAction?) {
    private val actionId: Long
    private val description: String
    private val creatorNickname: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        actionId = entity.getActionId()
        description = entity.getDescription()
        creatorNickname = entity.getMember().getNickname()
    }
}