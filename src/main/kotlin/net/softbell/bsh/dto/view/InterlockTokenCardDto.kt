package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.MemberInterlockToken
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 연동 토큰뷰 카드정보 DTO
 */
class InterlockTokenCardDto(entity: MemberInterlockToken) {
    val tokenId: Long = entity.memberInterlockId
    val name: String = entity.name
    val token: String = entity.token
    val enableStatus: EnableStatusRule = entity.enableStatus
    val registerDate: Date = entity.registerDate
}