package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 차단 상태 자료형
 */
@AllArgsConstructor
@Getter
enum class BanRule {
    NORMAL("NORMAL", 0), PERMANENT("PERMANENT", -1), TEMP("TEMP", 1);

    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): BanRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}