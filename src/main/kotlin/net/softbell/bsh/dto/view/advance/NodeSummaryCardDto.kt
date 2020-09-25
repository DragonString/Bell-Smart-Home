package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드뷰 리스트 카드정보 DTO
 */
class NodeSummaryCardDto(entity: Node?) {
    private val nodeId: Long
    private val alias: String
    private val enableStatus: EnableStatusRule
    private val controlMode: Byte

    init {
        // Exception
        if (entity == null) return

        // Convert
        nodeId = entity.getNodeId()
        alias = entity.getAlias()
        enableStatus = entity.getEnableStatus()
        controlMode = entity.getControlMode()
    }
}