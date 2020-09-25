package net.softbell.bsh.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MemberDto {
    private val memberId: Long? = null
    private val ban: Int? = null
    private val banDate: Date? = null
    private val changePasswdDate: Date? = null
    private val email: String? = null
    private val lastLogin: Date? = null
    private val nickname: String? = null
    private val password: String? = null
    private val permission: Int? = null
    private val registerDate: Date? = null
    private val userId: String? = null
    private val username: String? = null
    fun toEntity(): Member {
        // Generate
        return builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(username)
                .nickname(nickname)
                .registerDate(Date())
                .ban(BanRule.Companion.ofLegacyCode(ban))
                .permission(MemberRole.Companion.ofLegacyCode(permission))
                .build()
    }
}