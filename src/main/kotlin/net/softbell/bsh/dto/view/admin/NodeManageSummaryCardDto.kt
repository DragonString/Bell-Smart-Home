package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.EnableStatusRule

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리뷰 리스트 카드정보 DTO
 */
class NodeManageSummaryCardDto(entity: Node?) {
    private val nodeId: Long
    private val uid: String
    private val alias: String
    private val enableStatus: EnableStatusRule

    init {
        // Exception
        if (entity == null) return

        // Convert
        nodeId = entity.getNodeId()
        uid = entity.getUid()
        alias = entity.getAlias()
        enableStatus = entity.getEnableStatus()
    }
}