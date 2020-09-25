package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 자료형
 */
enum class TriggerLastStatusRule {
    WAIT {  // 최초 등록 후 대기
        override val value: String
            get() = "WAIT"
        override val code: Int
            get() = 0
    },
    OCCUR {  // 트리거 발동
        override val value: String
            get() = "OCCUR"
        override val code: Int
            get() = 1
    },
    RESTORE {  // 트리거 복구
        override val value: String
            get() = "RESTORE"
        override val code: Int
            get() = 2
    },
    ERROR {  // 에러
        override val value: String
            get() = "ERROR"
        override val code: Int
            get() = -1
    };


    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): TriggerLastStatusRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}