package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 그룹 회원 정보 DTO
 */
data class MemberGroupDto (
        var enableStatus: Boolean,
        var name: String,
        var memberId: List<Long>
)