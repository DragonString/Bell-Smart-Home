package net.softbell.bsh.dto.request

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원정보 DTO
 */
class MemberDto {
    var memberId: Long? = null
    var ban: Int? = null
    var banDate: Date? = null
    var changePasswdDate: Date? = null
    var email: String? = null
    var lastLogin: Date? = null
    var nickname: String? = null
    var password: String = ""
    var permission: Int? = null
    var registerDate: Date? = null
    var userId: String? = null
    var username: String? = null

    fun toEntity(): Member {
        // Generate
        var member = Member()

        member.userId = userId
        member.email = email
        member.password = password
        member.name = username
        member.nickname = nickname
        member.registerDate = Date()
        member.ban = BanRule.ofLegacyCode(ban!!)
        member.permission = MemberRole.ofLegacyCode(permission!!)

        return member
    }
}