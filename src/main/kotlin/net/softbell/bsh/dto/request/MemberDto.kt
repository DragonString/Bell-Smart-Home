package net.softbell.bsh.dto.request

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원정보 DTO
 */
class MemberDto(
        //    var memberId: Long = null
        var ban: Int = 0,
        var banDate: Date?,
        var changePasswdDate: Date?,
        var email: String,
        var lastLogin: Date?,
        var nickname: String,
        var password: String,
        var permission: Int = 0,
        var registerDate: Date = Date(),
        var userId: String,
        var username: String
) {
    fun toEntity(): Member {
        // Generate
        return Member(
                userId = userId,
                email = email,
                password = password,
                name = username,
                nickname = nickname,
                registerDate = registerDate,
                ban = BanRule.ofLegacyCode(ban),
                permission = MemberRole.ofLegacyCode(permission)
        )
    }
}