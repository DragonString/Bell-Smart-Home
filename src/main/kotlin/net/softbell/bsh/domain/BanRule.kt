package net.softbell.bsh.domain

/**
 * @author : Bell(bell@softbell.net)
 * @description : 차단 상태 자료형
 */
enum class BanRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Int
            get() = -1
    },
    NORMAL { // 미차단
        override val value: String
            get() = "NORMAL"
        override val code: Int
            get() = 0
    },
    TEMP { // 임시 차단
        override val value: String
            get() = "TEMP"
        override val code: Int
            get() = 1
    },
    PERMANENT { // 영구 차단
        override val value: String
        get() = "PERMANENT"
        override val code: Int
        get() = 2
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): BanRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}