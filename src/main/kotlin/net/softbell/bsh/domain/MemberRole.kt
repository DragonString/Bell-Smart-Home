package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 권한 자료형
 */
enum class MemberRole() {
    SUPERADMIN { // 최고 관리자
        override val value: String
            get() = "ROLE_SUPERADMIN"
        override val code: Int
            get() = 100
    },
    ADMIN { // 관리자
        override val value: String
            get() = "ROLE_ADMIN"
        override val code: Int
            get() = 10
    },
    MEMBER { // 일반 회원
        override val value: String
            get() = "ROLE_MEMBER"
        override val code: Int
            get() = 5
    },
    NODE { // 노드 전용 계정
        override val value: String
            get() = "ROLE_NODE"
        override val code: Int
            get() = 1
    },
    WAIT { // 승인 대기
        override val value: String
            get() = "ROLE_WAIT"
        override val code: Int
            get() = 0
    },
    BAN { // 차단
        override val value: String
            get() = "ROLE_BAN"
        override val code: Int
            get() = -1
    };

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): MemberRole? {
            for (rule in values()) if (rule.code === legacyCode) return rule
            return null
        }
    }
}