package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 권한 정보 DTO
 */
data class NodeGroupPermissionDto (
        var memberGid: Long? = null,
        var permission: Int? = null
)