package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.entity.MemberLoginLog
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 활동 로그뷰 카드정보 DTO
 */
class MemberActivityLogCardDto(entity: MemberLoginLog?) {
    var requestDate: Date?
    var ipv4: String?
    var status: AuthStatusRule?

    init {
        // Exception
        entity.let {
            // Convert
            requestDate = entity!!.requestDate
            ipv4 = entity.ipv4
            status = entity.status
        }
    }
}