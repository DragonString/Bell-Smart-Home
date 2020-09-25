package net.softbell.bsh.dto.view.general

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeActionItem
import net.softbell.bsh.domain.entity.NodeItem
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
@Getter
@Setter
class ActionItemCardDto {
    private var itemId: Long
    private var nodeId: Long
    private var nodeAlias: String
    private var itemAlias: String
    private var pinStatus: Double? = null
    private var pinMin: Long
    private var pinMax: Long

    constructor(entity: NodeItem?) {
        // Exception
        if (entity == null) return

        // Convert
        itemId = entity.getItemId()
        nodeId = entity.getNode().getNodeId()
        nodeAlias = entity.getNode().getAlias()
        itemAlias = entity.getAlias()
        pinMin = 0L
        pinMax = 1024L // TODO 핀 타입에 따라 맥스값 지정해주기
    }

    constructor(entity: NodeActionItem?) {
        // Exception
        if (entity == null) return

        // Convert
        itemId = entity.getNodeItem().getItemId()
        nodeId = entity.getNodeItem().getNode().getNodeId()
        nodeAlias = entity.getNodeItem().getNode().getAlias()
        itemAlias = entity.getNodeItem().getAlias()
        pinStatus = entity.getItemStatus()
        pinMin = 0L
        pinMax = 1024L
    }
}