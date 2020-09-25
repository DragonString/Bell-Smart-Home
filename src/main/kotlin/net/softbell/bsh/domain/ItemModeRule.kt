package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 모드 자료형
 * (디지털, 아날로그)
 */
@AllArgsConstructor
@Getter
enum class ItemModeRule {
    ERROR("ERROR", -1), DIGITAL("DIGITAL", 0), ANALOG("ANALOG", 1);

    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): ItemModeRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}