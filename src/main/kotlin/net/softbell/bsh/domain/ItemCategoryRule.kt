package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 카테고리 자료형
 * (사용자, 제어, 센서, 리더)
 */
enum class ItemCategoryRule {
    ERROR {
        override val value: String
            get() = "ERROR"
        override val code: Int
            get() = -1
    },
    USER {
        override val value: String
            get() = "USER"
        override val code: Int
            get() = 0
    },
    CONTROL {
        override val value: String
            get() = "CONTROL"
        override val code: Int
            get() = 10
    },
    SENSOR {
        override val value: String
            get() = "SENSOR"
        override val code: Int
            get() = 20
    },
    READER {
        override val value: String
            get() = "READER"
        override val code: Int
            get() = 30
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): ItemCategoryRule? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}