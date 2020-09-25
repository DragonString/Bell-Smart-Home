package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 권한 정보 DTO
 */
data class NodeGroupPermissionDto (
        val memberGid: Long? = null,
        val permission: Int? = null
)