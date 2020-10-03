package net.softbell.bsh.domain

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 아이템 카테고리 자료형
 * (사용자, 제어, 센서, 리더)
 */
enum class ItemCategoryRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Int
            get() = -1
    },
    USER { // 사용자
        override val value: String
            get() = "USER"
        override val code: Int
            get() = 0
    },
    CONTROL { // 제어
        override val value: String
            get() = "CONTROL"
        override val code: Int
            get() = 10
    },
    SENSOR { // 센서
        override val value: String
            get() = "SENSOR"
        override val code: Int
            get() = 20
    },
    READER { // 리더
        override val value: String
            get() = "READER"
        override val code: Int
            get() = 30
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): ItemCategoryRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}