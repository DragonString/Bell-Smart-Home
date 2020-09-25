package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 권한 자료형
 */
@AllArgsConstructor
@Getter
enum class MemberRole {
    SUPERADMIN("ROLE_SUPERADMIN", 100),  // 최고 관리자
    ADMIN("ROLE_ADMIN", 10),  // 관리자
    MEMBER("ROLE_MEMBER", 5),  // 일반 회원
    NODE("ROLE_NODE", 1),  // 노드 전용 계정
    WAIT("ROLE_WAIT", 0),  // 승인 대기
    BAN("ROLE_BAN", -1);

    // 차단
    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): MemberRole? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}