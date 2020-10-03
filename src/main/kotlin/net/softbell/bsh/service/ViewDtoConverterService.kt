package net.softbell.bsh.service

import net.softbell.bsh.domain.entity.*
import net.softbell.bsh.dto.view.InterlockTokenCardDto
import net.softbell.bsh.dto.view.MemberActivityLogCardDto
import net.softbell.bsh.dto.view.admin.*
import net.softbell.bsh.dto.view.admin.group.MemberGroupSummaryCardDto
import net.softbell.bsh.dto.view.admin.group.NodeGroupSummaryCardDto
import net.softbell.bsh.dto.view.advance.NodeItemCardDto
import net.softbell.bsh.dto.view.advance.NodeSummaryCardDto
import net.softbell.bsh.dto.view.advance.TriggerItemCardDto
import net.softbell.bsh.dto.view.advance.TriggerSummaryCardDto
import net.softbell.bsh.dto.view.general.*
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 엔티티 to 뷰 DTO 변환 서비스
 */
@Service
class ViewDtoConverterService {
    // Global Field
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1

    // Node Entity List to Monitor Card Dto List
    fun convMonitorSummaryCards(listEntity: Collection<Node>): List<MonitorSummaryCardDto> {
        // Init
        val listCards: MutableList<MonitorSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity) {
            // Init
            val cardDto = MonitorSummaryCardDto(entity)
            val curDate = Date()

            // Process
            for (nodeItem in entity.nodeItems) {
                // Init
                val nodeItemHistory = iotNodeService.getLastNodeItemHistory(nodeItem) ?: continue
                val lastHistory = iotNodeService.getLastNodeItemHistory(nodeItem) ?: continue

                cardDto.listItems.add(MonitorCardItemDto(nodeItem, nodeItemHistory))

                // 제일 최근 수신시각 계산
                if (cardDto.lastReceive == null || lastHistory.receiveDate > cardDto.lastReceive)
                    cardDto.lastReceive = lastHistory.receiveDate
            }
            cardDto.lastReceive.let {
                if (it != null) {
                    cardDto.lastReceiveSecond = (curDate.time - it.time) / 1000
                }
            }

            // Add
            listCards.add(cardDto)
        }

        // Return
        return listCards
    }

    // NodeReserv Entity List to Reserv Card Dto List
    fun convReservSummaryCards(listEntity: Collection<NodeReserv>): List<ReservSummaryCardDto> {
        // Init
        val listCards: MutableList<ReservSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(ReservSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeAction Entity List to Reserv Action Card Dto List
    fun <T> convReservActionCards(listEntity: Collection<T>): List<ReservActionCardDto> {
        // Init
        val listCards: MutableList<ReservActionCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            if (entity is NodeAction)
                listCards.add(ReservActionCardDto(entity as NodeAction))
            else if (entity is NodeReservAction)
                listCards.add(ReservActionCardDto((entity as NodeReservAction).nodeAction))

        // Return
        return listCards
    }

    // NodeAction Entity List to Action Summary Card Dto List
    fun convActionSummaryCards(listEntity: Collection<NodeAction>): List<ActionSummaryCardDto> {
        // Init
        val listCards: MutableList<ActionSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(ActionSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Action Item Card Dto List
    fun <T> convActionItemCards(listEntity: Collection<T>): List<ActionItemCardDto> {
        // Init
        val listCards: MutableList<ActionItemCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            if (entity is NodeItem)
                listCards.add(ActionItemCardDto(entity as NodeItem))
            else if (entity is NodeActionItem)
                listCards.add(ActionItemCardDto(entity as NodeActionItem))

        // Return
        return listCards
    }

    // Node Entity List to Node Summary Card Dto List
    fun <T> convNodeSummaryCards(listEntity: Collection<T>): List<NodeSummaryCardDto> {
        // Init
        val listCards: MutableList<NodeSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(NodeSummaryCardDto(entity as Node))

        // Return
        return listCards
    }

    // NodeItem Entity List to Node Item Card Dto List
    fun convNodeItemCards(listEntity: Collection<NodeItem>): List<NodeItemCardDto> {
        // Init
        val listCards: MutableList<NodeItemCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
        {
            val lastHistory = iotNodeService.getLastNodeItemHistory(entity) ?: continue
            listCards.add(NodeItemCardDto(entity, lastHistory))
        }

        // Return
        return listCards
    }

    // Member Entity List to Member Summary Card Dto List
    fun convMemberSummaryCards(listEntity: Collection<Member>): List<MemberSummaryCardDto> {
        // Init
        val listCards: MutableList<MemberSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(MemberSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberLoginLog Entity List to Member Activity Log Card Dto List
    fun convMemberActivityLogCards(listEntity: Collection<MemberLoginLog>): List<MemberActivityLogCardDto> {
        // Init
        val listCards: MutableList<MemberActivityLogCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(MemberActivityLogCardDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Node Manage Summary Card Dto List
    fun convNodeManageSummaryCards(listEntity: Collection<Node>): List<NodeManageSummaryCardDto> {
        // Init
        val listCards: MutableList<NodeManageSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(NodeManageSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Node Manage Item Card Dto List
    fun convNodeManageItemCards(listEntity: Collection<NodeItem>): List<NodeManageItemCardDto> {
        // Init
        val listCards: MutableList<NodeManageItemCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(NodeManageItemCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Trigger Item Card Dto List
    fun convTriggerItemCards(listEntity: Collection<NodeItem>): List<TriggerItemCardDto> {
        // Init
        val listCards: MutableList<TriggerItemCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(TriggerItemCardDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Node Manage Summary Card Dto List
    fun convTriggerSummaryCards(listEntity: Collection<NodeTrigger>): List<TriggerSummaryCardDto> {
        // Init
        val listCards: MutableList<TriggerSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(TriggerSummaryCardDto(entity))

        // Return
        return listCards
    }

    // Member Entity List to Group Member Card Item Dto List
    fun convGroupMemberCardItems(listEntity: Collection<Member>): List<GroupMemberCardItemDto> {
        // Init
        val listCards: MutableList<GroupMemberCardItemDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(GroupMemberCardItemDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Group Node Card Item Dto List
    fun convGroupNodeCardItems(listEntity: Collection<Node>): List<GroupNodeCardItemDto> {
        // Init
        val listCards: MutableList<GroupNodeCardItemDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(GroupNodeCardItemDto(entity))

        // Return
        return listCards
    }

    // NodeGroup Entity List to Node Group Summary Card Dto List
    fun convNodeGroupSummaryCards(listEntity: Collection<NodeGroup>): List<NodeGroupSummaryCardDto> {
        // Init
        val listCards: MutableList<NodeGroupSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(NodeGroupSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberGroup Entity List to Member Group Summary Card Dto List
    fun convMemberGroupSummaryCards(listEntity: Collection<MemberGroup>): List<MemberGroupSummaryCardDto> {
        // Init
        val listCards: MutableList<MemberGroupSummaryCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(MemberGroupSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberInterlockToken Entity List to Interlock Token Card Dto List
    fun convInterlockTokenCards(listEntity: Collection<MemberInterlockToken>): List<InterlockTokenCardDto> {
        // Init
        val listCards: MutableList<InterlockTokenCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(InterlockTokenCardDto(entity))

        // Return
        return listCards
    }

    // MemberInterlockToken Entity List to Action Interlock Token Card Dto List
    fun convActionInterlockTokenCards(listEntity: Collection<MemberInterlockToken>): List<ActionInterlockCardDto> {
        // Init
        val listCards: MutableList<ActionInterlockCardDto> = ArrayList()

        // Process
        for (entity in listEntity)
            listCards.add(ActionInterlockCardDto(entity))

        // Return
        return listCards
    }
}