package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드뷰 리스트 카드정보 DTO
 */
class NodeSummaryCardDto(entity: Node) {
    val nodeId: Long = entity.nodeId
    val alias: String = entity.alias
    val enableStatus: EnableStatusRule = entity.enableStatus
    val controlMode: Byte = entity.controlMode
}