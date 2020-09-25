package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeGroup

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹뷰 요약 카드정보 DTO
 */
class NodeGroupSummaryCardDto(entity: NodeGroup?) {
    private val gid: Long
    private val name: String
    private val enableStatus: EnableStatusRule
    private val listNode: MutableList<NodeGroupItemDto>

    inner class NodeGroupItemDto(entity: Node) {
        private val nodeId: Long
        private val alias: String

        init {
            nodeId = entity.getNodeId()
            alias = entity.getAlias()
        }
    }

    init {
        // Exception
        if (entity == null) return

        // Init
        listNode = ArrayList()

        // Convert
        gid = entity.getNodeGroupId()
        name = entity.getName()
        enableStatus = entity.getEnableStatus()
        for (child in entity.getNodeGroupItems()) listNode.add(NodeGroupItemDto(child.getNode()))
    }
}