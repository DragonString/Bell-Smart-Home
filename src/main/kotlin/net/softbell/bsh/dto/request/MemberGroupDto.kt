package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 회원 정보 DTO
 */
data class MemberGroupDto (
        var enableStatus: Boolean,
        var name: String,
        var memberId: List<Long>
)