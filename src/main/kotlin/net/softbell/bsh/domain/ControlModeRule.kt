package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 제어 모드 비트플래그 자료형
 */
@AllArgsConstructor
@Getter
enum class ControlModeRule {
    INFO_SYNC_DISABLE("INFO_SYNC_DISABLE", 1.toByte()), AUTO_CONTROL_DISABLE("AUTO_CONTROL_DISABLE", 2.toByte()), MANUAL_CONTROL_DISABLE("MANUAL_CONTROL_DISABLE", 4.toByte());

    private val value: String? = null
    private val code: Byte? = null

    companion object {
        fun ofLegacyCode(legacyCode: Byte?): ControlModeRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}