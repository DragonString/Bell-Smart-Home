package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.entity.MemberLoginLog
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 활동 로그뷰 카드정보 DTO
 */
class MemberActivityLogCardDto(entity: MemberLoginLog) {
    val requestDate: Date = entity.requestDate
    val ipv4: String = entity.ipv4
    val status: AuthStatusRule = entity.status
}