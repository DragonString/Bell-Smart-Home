package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 활성화 상태 자료형
 */
@AllArgsConstructor
@Getter
enum class EnableStatusRule {
    WAIT("WAIT", 0),  // 인증 대기
    DISABLE("DISABLE", 1),  // 비활성화
    ENABLE("ENABLE", 2),  // 활성화
    REJECT("REJECT", -1);

    // 접근 제한
    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): EnableStatusRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}