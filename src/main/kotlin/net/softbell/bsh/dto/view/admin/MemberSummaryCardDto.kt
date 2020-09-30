package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 관리뷰 요약 카드정보 DTO
 */
class MemberSummaryCardDto(entity: Member?) {
    var memberId: Long?
    var userId: String?
    var username: String
    var registerDate: Date?
    var ban: BanRule?
    var permission: MemberRole?

    init {
        // Exception
        entity.let {
            // Convert
            memberId = entity!!.memberId
            userId = entity.userId
            username = entity.username
            registerDate = entity.registerDate
            ban = entity.ban
            permission = entity.permission
        }
    }
}