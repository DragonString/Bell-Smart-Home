package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.MemberInterlockToken
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰뷰 카드정보 DTO
 */
class InterlockTokenCardDto(entity: MemberInterlockToken?) {
    var tokenId: Long?
    var name: String?
    var token: String?
    var enableStatus: EnableStatusRule?
    var registerDate: Date?

    init {
        // Exception
        entity.let {
            // Convert
            tokenId = entity!!.memberInterlockId
            name = entity.name
            token = entity.token
            enableStatus = entity.enableStatus
            registerDate = entity.registerDate
        }
    }
}