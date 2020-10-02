package net.softbell.bsh.dto.request

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원정보 DTO
 */
class MemberDto(
        ban: Int,
        banDate: Date,
        changePasswdDate: Date,
        email: String,
        lastLogin: Date,
        nickname: String,
        password: String,
        permission: Int,
        registerDate: Date = Date(),
        userId: String,
        username: String
) {
//    var memberId: Long = null
    var ban: Int = ban
    var banDate: Date? = banDate
    var changePasswdDate: Date? = changePasswdDate
    var email: String = email
    var lastLogin: Date? = lastLogin
    var nickname: String = nickname
    var password: String = password
    var permission: Int = permission
    var registerDate: Date = registerDate
    var userId: String = userId
    var username: String = nickname

    fun toEntity(): Member {
        // Generate
        var member = Member(
                userId = userId,
                email = email,
                password = password,
                name = username,
                nickname = nickname,
                registerDate = registerDate,
                ban = BanRule.ofLegacyCode(ban),
                permission = MemberRole.ofLegacyCode(permission)
        )

        return member
    }
}