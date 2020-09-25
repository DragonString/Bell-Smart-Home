package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 자료형
 */
@AllArgsConstructor
@Getter
enum class TriggerLastStatusRule {
    WAIT("WAIT", 0),  // 최초 등록 후 대기
    OCCUR("OCCUR", 1),  // 트리거 발동
    RESTORE("RESTORE", 2),  // 트리거 복구
    ERROR("ERROR", -1);

    // 에러
    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): TriggerLastStatusRule? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}