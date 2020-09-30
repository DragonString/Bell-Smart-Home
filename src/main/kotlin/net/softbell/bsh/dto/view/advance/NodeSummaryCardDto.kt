package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드뷰 리스트 카드정보 DTO
 */
class NodeSummaryCardDto(entity: Node?) {
    var nodeId: Long?
    var alias: String?
    var enableStatus: EnableStatusRule?
    var controlMode: Byte?

    init {
        // Exception
        entity.let {
            // Convert
            nodeId = entity!!.nodeId
            alias = entity.alias
            enableStatus = entity.enableStatus
            controlMode = entity.controlMode
        }
    }
}