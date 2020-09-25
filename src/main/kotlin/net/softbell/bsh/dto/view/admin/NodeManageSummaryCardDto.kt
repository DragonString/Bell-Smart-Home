package net.softbell.bsh.dto.view.admin

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리뷰 리스트 카드정보 DTO
 */
@Getter
@Setter
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