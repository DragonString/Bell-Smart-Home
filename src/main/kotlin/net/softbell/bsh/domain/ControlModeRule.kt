package net.softbell.bsh.domain

/**
 * @author : Bell(bell@softbell.net)
 * @description : 제어 모드 비트플래그 자료형
 */
enum class ControlModeRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Byte
            get() = -1
    },
    INFO_SYNC_DISABLE {
        override val value: String
            get() = "INFO_SYNC_DISABLE"
        override val code: Byte
            get() = 1
    },
    AUTO_CONTROL_DISABLE {
        override val value: String
            get() = "AUTO_CONTROL_DISABLE"
        override val code: Byte
            get() = 2
    },
    MANUAL_CONTROL_DISABLE {
        override val value: String
            get() = "MANUAL_CONTROL_DISABLE"
        override val code: Byte
            get() = 4
    };

    abstract val value: String
    abstract val code: Byte

    companion object {
        fun ofLegacyCode(legacyCode: Byte): ControlModeRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}