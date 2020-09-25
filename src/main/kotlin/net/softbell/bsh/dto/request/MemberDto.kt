package net.softbell.bsh.dto.request

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원정보 DTO
 */
data class MemberDto (
        val memberId: Long? = null,
        val ban: Int? = null,
        val banDate: Date? = null,
        val changePasswdDate: Date? = null,
        val email: String? = null,
        val lastLogin: Date? = null,
        val nickname: String? = null,
        val password: String? = null,
        val permission: Int? = null,
        val registerDate: Date? = null,
        val userId: String? = null,
        val username: String? = null

//    fun toEntity(): Member {
//        // Generate
//        return builder()
//                .userId(userId)
//                .email(email)
//                .password(password)
//                .name(username)
//                .nickname(nickname)
//                .registerDate(Date())
//                .ban(BanRule.Companion.ofLegacyCode(ban))
//                .permission(MemberRole.Companion.ofLegacyCode(permission))
//                .build()
//    }
)