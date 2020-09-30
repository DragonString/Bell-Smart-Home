package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 상태 자료형
 */
enum class TriggerStatusRule {
    ALL {  // 전체 0b0
        override val value: String
            get() = "ALL"
        override val code: Int
            get() = 0
    },
    ERROR {  // 에러시 0b0001
        override val value: String
            get() = "ERROR"
        override val code: Int
            get() = 1
    },
    OCCUR {  // 트리거 발생 0b0010
        override val value: String
            get() = "OCCUR"
        override val code: Int
            get() = 2
    },
    RESTORE {  // 트리거 복구 0b0100
        override val value: String
            get() = "RESTORE"
        override val code: Int
            get() = 4
    },
    OCCUR_AND_RESTORE {  // 트리거 발생 및 복구 0b0110
        override val value: String
            get() = "OCCUR_AND_RESTORE"
        override val code: Int
            get() = 6
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): TriggerStatusRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}