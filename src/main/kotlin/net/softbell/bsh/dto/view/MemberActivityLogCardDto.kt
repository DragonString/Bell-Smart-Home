package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.entity.MemberLoginLog

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 활동 로그뷰 카드정보 DTO
 */
class MemberActivityLogCardDto(entity: MemberLoginLog?) {
    private val requestDate: Date
    private val ipv4: String
    private val status: AuthStatusRule

    init {
        // Exception
        if (entity == null) return

        // Convert
        requestDate = entity.getRequestDate()
        ipv4 = entity.getIpv4()
        status = entity.getStatus()
    }
}