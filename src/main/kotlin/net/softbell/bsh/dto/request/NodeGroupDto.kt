package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 그룹 노드 정보 DTO
 */
data class NodeGroupDto (
        var enableStatus: Boolean,
        var name: String,
        var nodeId: List<Long>
)