package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 프로필뷰 카드정보 DTO
 */
class MemberProfileCardDto(entity: Member?) {
    var name: String?
    var nickname: String?
    var userId: String?
    var email: String?
    var ban: BanRule?
    var permission: MemberRole?
    var registerDate: Date?

    init {
        // Exception
        entity.let {
            // Convert
            name = entity!!.name
            nickname = entity.nickname
            userId = entity.userId
            email = entity.email
            ban = entity.ban
            permission = entity.permission
            registerDate = entity.registerDate
        }
    }
}