package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.MemberInterlockToken

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 연동 정보 카드정보 DTO
 */
class ActionInterlockCardDto(entity: MemberInterlockToken?) {
    var name: String?
    var token: String?

    init {
        // Exception
        entity.let {
            // Convert
            name = entity!!.name
            token = entity.token
        }
    }
}