package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 권한 정보 DTO
 */
data class MemberGroupPermissionDto (
        val nodeGid: Long? = null,
        val permission: Int? = null
)