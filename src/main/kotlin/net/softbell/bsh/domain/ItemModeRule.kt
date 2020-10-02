package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 모드 자료형
 * (디지털, 아날로그)
 */
enum class ItemModeRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Int
            get() = -1
    },
    DIGITAL { // 디지털
        override val value: String
            get() = "DIGITAL"
        override val code: Int
            get() = 0
    },
    ANALOG { // 아날로그
        override val value: String
            get() = "ANALOG"
        override val code: Int
            get() = 1
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): ItemModeRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}