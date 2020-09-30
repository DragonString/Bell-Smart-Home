package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeActionItem
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
class ActionItemCardDto {
    var itemId: Long? = null
    var nodeId: Long? = null
    var nodeAlias: String? = null
    var itemAlias: String? = null
    var pinStatus: Double? = null
    var pinMin: Long = 0
    var pinMax: Long = 0

    constructor(entity: NodeItem?) {
        // Exception
        if (entity == null) return

        // Convert
        itemId = entity.itemId
        nodeId = entity.node!!.nodeId
        nodeAlias = entity.node!!.alias
        itemAlias = entity.alias
        pinMin = 0L
        pinMax = 1024L // TODO 핀 타입에 따라 맥스값 지정해주기
    }

    constructor(entity: NodeActionItem?) {
        // Exception
        if (entity == null) return

        // Convert
        itemId = entity.nodeItem!!.itemId
        nodeId = entity.nodeItem!!.node!!.nodeId
        nodeAlias = entity.nodeItem!!.node!!.alias
        itemAlias = entity.nodeItem!!.alias
        pinStatus = entity.itemStatus
        pinMin = 0L
        pinMax = 1024L
    }
}