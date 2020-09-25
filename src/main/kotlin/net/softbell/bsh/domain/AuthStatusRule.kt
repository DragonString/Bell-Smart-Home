package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
@AllArgsConstructor
@Getter
enum class AuthStatusRule {
    SUCCESS("SUCCESS", 0),  // 정상 인증
    FAIL("FAIL", 1),  // 인증 실패
    ERROR("ERROR", -1);

    // 에러
    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): AuthStatusRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}