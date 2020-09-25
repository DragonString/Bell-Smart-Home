package net.softbell.bsh.domain

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 자료형
 */
@AllArgsConstructor
@Getter
enum class GroupRole {
    MONITOR("MONITOR", 0),  // 모니터링 기능 이용 가능
    MANUAL_CONTROL("MANUAL_CONTROL", 1),  // 컨트롤 패널에서 수동 조작 가능
    ACTION("ACTION", 2),  // 액션 이용 가능
    RESERV("RESERV", 3),  // 예약기능 이용 가능
    TRIGGER("TRIGGER", 4);

    // 트리거 이용 가능
    private val value: String? = null
    private val code: Int? = null

    companion object {
        fun ofLegacyCode(legacyCode: Int?): GroupRole? {
            for (authStatusRule in values()) if (authStatusRule.getCode() === legacyCode) return authStatusRule
            return null
        }
    }
}