package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
enum class AuthStatusRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Int
            get() = -1
    },
    SUCCESS { // 정상 인증
        override val value: String
            get() = "SUCCESS"
        override val code: Int
            get() = 0
    },
    FAIL { // 인증 실패
        override val value: String
            get() = "FAIL"
        override val code: Int
            get() = 1
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): AuthStatusRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}