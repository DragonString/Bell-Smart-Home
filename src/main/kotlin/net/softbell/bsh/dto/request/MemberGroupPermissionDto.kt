package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 그룹 권한 정보 DTO
 */
data class MemberGroupPermissionDto (
        var nodeGid: Long,
        var permission: Int
)