package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 차단 상태 자료형
 */
enum class BanRule {
    NORMAL {
        override val value: String
            get() = "NORMAL"
        override val code: Int
            get() = 0
    },
    PERMANENT {
        override val value: String
            get() = "PERMANENT"
        override val code: Int
            get() = -1
    },
    TEMP {
        override val value: String
            get() = "TEMP"
        override val code: Int
            get() = 1
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): BanRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}