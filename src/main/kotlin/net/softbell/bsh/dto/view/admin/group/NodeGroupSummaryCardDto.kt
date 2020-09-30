package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹뷰 요약 카드정보 DTO
 */
class NodeGroupSummaryCardDto(entity: NodeGroup?) {
    var gid: Long?
    var name: String?
    var enableStatus: EnableStatusRule?
    var listNode: MutableList<NodeGroupItemDto>

    inner class NodeGroupItemDto(entity: Node?) {
        var nodeId: Long?
        var alias: String?

        init {
            nodeId = entity!!.nodeId
            alias = entity.alias
        }
    }

    init {
        // Exception
        entity.let {
            // Init
            listNode = ArrayList()

            // Convert
            gid = entity!!.nodeGroupId
            name = entity.name
            enableStatus = entity.enableStatus
            for (child in entity.nodeGroupItems!!) listNode.add(NodeGroupItemDto(child.node))
        }
    }
}