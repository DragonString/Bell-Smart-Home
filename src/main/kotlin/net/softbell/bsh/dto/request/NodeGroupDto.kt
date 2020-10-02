package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 정보 DTO
 */
data class NodeGroupDto (
        var enableStatus: Boolean = false,
        var name: String,
        var nodeId: List<Long>? = null
)