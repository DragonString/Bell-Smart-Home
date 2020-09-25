package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.MemberInterlockToken

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 연동 정보 카드정보 DTO
 */
class ActionInterlockCardDto(entity: MemberInterlockToken?) {
    private val name: String
    private val token: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        name = entity.getName()
        token = entity.getToken()
    }
}