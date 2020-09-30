package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 히스토리뷰 카드정보 DTO
 */
class NodeItemHistoryCardDto(entity: NodeItem?, listNodeItemHistory: List<NodeItemHistory?>) {
    var alias: String?
    var listItems: MutableList<NodeItemHistoryCardItemDto>

    inner class NodeItemHistoryCardItemDto(entity: NodeItemHistory?) {
        var receiveDate: Date?
        var pinStatus: Double?

        init {
            // Exception
            entity.let {
                // Convert
                receiveDate = entity!!.receiveDate
                pinStatus = entity.itemStatus
            }
        }
    }

    init {
        // Exception
        entity.let {
            // Init
            listItems = ArrayList()

            // Convert
            alias = entity!!.alias
            for (nodeItem in listNodeItemHistory) listItems.add(NodeItemHistoryCardItemDto(nodeItem))
        }
    }
}