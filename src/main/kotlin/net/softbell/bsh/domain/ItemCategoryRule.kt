package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 카테고리 자료형
 * (사용자, 제어, 센서, 리더)
 */
@AllArgsConstructor
@Getter
enum class ItemCategoryRule {
    ERROR("ERROR", -1), USER("USER", 0), CONTROL("CONTROL", 10), SENSOR("SENSOR", 20), READER("READER", 30);

    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): ItemCategoryRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}