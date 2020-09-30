package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리뷰 리스트 카드정보 DTO
 */
class NodeManageSummaryCardDto(entity: Node?) {
    var nodeId: Long?
    var uid: String?
    var alias: String?
    var enableStatus: EnableStatusRule?

    init {
        // Exception
        entity.let {
            // Convert
            nodeId = entity!!.nodeId
            uid = entity.uid
            alias = entity.alias
            enableStatus = entity.enableStatus
        }
    }
}