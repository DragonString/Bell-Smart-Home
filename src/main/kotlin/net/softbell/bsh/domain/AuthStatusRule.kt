package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
enum class AuthStatusRule {
    SUCCESS {  // 정상 인증
        override val value: String
            get() = "SUCCESS"
        override val code: Int
            get() = 0
    },
    FAIL{  // 인증 실패
        override val value: String
            get() = "FAIL"
        override val code: Int
            get() = 1
    },
    ERROR {  // 에러
        override val value: String
            get() = "ERROR"
        override val code: Int
            get() = -1
    };

    // 에러
    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): AuthStatusRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}