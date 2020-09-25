package net.softbell.bsh.dto.view.general

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.MemberInterlockToken
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 연동 정보 카드정보 DTO
 */
@Getter
@Setter
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