package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 아이템 히스토리뷰 카드정보 DTO
 */
class NodeItemHistoryCardDto(entity: NodeItem, listNodeItemHistory: List<NodeItemHistory>) {
    val alias: String = entity.alias
    val listItems: MutableList<NodeItemHistoryCardItemDto> = ArrayList()

    inner class NodeItemHistoryCardItemDto(entity: NodeItemHistory) {
        val receiveDate: Date = entity.receiveDate
        val pinStatus: Double = entity.itemStatus
    }

    init {
        // Convert
        for (nodeItem in listNodeItemHistory)
            listItems.add(NodeItemHistoryCardItemDto(nodeItem))
    }
}