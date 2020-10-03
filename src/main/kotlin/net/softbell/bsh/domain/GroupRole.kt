package net.softbell.bsh.domain

/**
 * @author : Bell(bell@softbell.net)
 * @description : 그룹 권한 자료형
 */
enum class GroupRole {
    MONITOR { // 모니터링 기능 이용 가능
        override val value: String
            get() = "MONITOR"
        override val code: Int
            get() = 0
    },
    MANUAL_CONTROL { // 컨트롤 패널에서 수동 조작 가능
        override val value: String
            get() = "MANUAL_CONTROL"
        override val code: Int
            get() = 1
    },
    ACTION { // 액션 이용 가능
        override val value: String
            get() = "ACTION"
        override val code: Int
            get() = 2
    },
    RESERV { // 예약기능 이용 가능
        override val value: String
            get() = "RESERV"
        override val code: Int
            get() = 3
    },
    TRIGGER { // 트리거 이용 가능
        override val value: String
            get() = "TRIGGER"
        override val code: Int
            get() = 4
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): GroupRole? {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return null
        }
    }
}