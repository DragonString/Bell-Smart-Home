package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 회원 정보 DTO
 */
data class MemberGroupDto (
        val enableStatus: Boolean = false,
        val name: String? = null,
        val memberId: List<Long>? = null
)