package net.softbell.bsh.dto.view.advance

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 히스토리뷰 카드정보 DTO
 */
@Getter
@Setter
class NodeItemHistoryCardDto(entity: NodeItem?, listNodeItemHistory: List<NodeItemHistory?>?) {
    private val alias: String
    private val listItems: MutableList<NodeItemHistoryCardItemDto>

    @Getter
    @Setter
    inner class NodeItemHistoryCardItemDto(entity: NodeItemHistory?) {
        private val receiveDate: Date
        private val pinStatus: Double

        init {
            // Exception
            if (entity == null) return

            // Convert
            receiveDate = entity.getReceiveDate()
            pinStatus = entity.getItemStatus()
        }
    }

    init {
        // Exception
        if (entity == null) return

        // Init
        listItems = ArrayList()

        // Convert
        alias = entity.getAlias()
        for (nodeItem in listNodeItemHistory!!) listItems.add(NodeItemHistoryCardItemDto(nodeItem))
    }
}