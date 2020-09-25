package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 정보 DTO
 */
data class NodeGroupDto (
        val enableStatus: Boolean = false,
        val name: String? = null,
        val nodeId: List<Long>? = null
)