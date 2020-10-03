package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 그룹 권한 정보 DTO
 */
data class NodeGroupPermissionDto (
        var memberGid: Long,
        var permission: Int
)