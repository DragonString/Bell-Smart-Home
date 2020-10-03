package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 그룹뷰 요약 카드정보 DTO
 */
class NodeGroupSummaryCardDto(entity: NodeGroup) {
    val gid: Long = entity.nodeGroupId
    val name: String = entity.name
    val enableStatus: EnableStatusRule = entity.enableStatus
    val listNode: MutableList<NodeGroupItemDto> = ArrayList()

    inner class NodeGroupItemDto(entity: Node) {
        val nodeId: Long = entity.nodeId
        val alias: String = entity.alias
    }

    init {
        // Convert
        for (child in entity.nodeGroupItems)
            listNode.add(NodeGroupItemDto(child.node))
    }
}