package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.NodeActionItem
import net.softbell.bsh.domain.entity.NodeItem

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
class ActionItemCardDto {
    val itemId: Long
    val nodeId: Long
    val nodeAlias: String
    val itemAlias: String
    val pinStatus: Double
    val pinMin: Long
    val pinMax: Long

    constructor(entity: NodeItem) {
        // Convert
        itemId = entity.itemId
        nodeId = entity.node.nodeId
        nodeAlias = entity.node.alias
        itemAlias = entity.alias
        pinStatus = 0.0
        pinMin = 0L
        pinMax = 1024L // TODO 핀 타입에 따라 맥스값 지정해주기
    }

    constructor(entity: NodeActionItem) {
        // Convert
        itemId = entity.nodeItem.itemId
        nodeId = entity.nodeItem.node.nodeId
        nodeAlias = entity.nodeItem.node.alias
        itemAlias = entity.nodeItem.alias
        pinStatus = entity.itemStatus
        pinMin = 0L
        pinMax = 1024L
    }
}