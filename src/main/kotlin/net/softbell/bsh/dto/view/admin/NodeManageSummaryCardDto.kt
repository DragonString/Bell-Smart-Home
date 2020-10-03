package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 관리뷰 리스트 카드정보 DTO
 */
class NodeManageSummaryCardDto(entity: Node) {
    val nodeId: Long = entity.nodeId
    val uid: String = entity.uid
    val alias: String = entity.alias
    val enableStatus: EnableStatusRule = entity.enableStatus
}