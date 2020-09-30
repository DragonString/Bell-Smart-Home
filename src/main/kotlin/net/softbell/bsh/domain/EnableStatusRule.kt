package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 활성화 상태 자료형
 */
enum class EnableStatusRule {
    WAIT { // 인증 대기
        override val value: String
            get() = "WAIT"
        override val code: Int
            get() = 0
    },
    DISABLE { // 비활성화
        override val value: String
            get() = "DISABLE"
        override val code: Int
            get() = 1
    },
    ENABLE { // 활성화
        override val value: String
            get() = "ENABLE"
        override val code: Int
            get() = 2
    },
    REJECT { // 접근 제한
        override val value: String
            get() = "REJECT"
        override val code: Int
            get() = -1
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): EnableStatusRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}