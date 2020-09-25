package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 제어 모드 비트플래그 자료형
 */
enum class ControlModeRule {
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
        fun ofLegacyCode(legacyCode: Byte): ControlModeRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}