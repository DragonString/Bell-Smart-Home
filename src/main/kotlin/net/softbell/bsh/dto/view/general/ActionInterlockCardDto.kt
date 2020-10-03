package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.MemberInterlockToken

/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션 연동 정보 카드정보 DTO
 */
class ActionInterlockCardDto(entity: MemberInterlockToken) {
    val name: String = entity.name
    val token: String = entity.token
}