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
 * @Author : Bell(bell@softbell.net)
 * @Description : 엔티티 to 뷰 DTO 변환 서비스
 */
@Service
class ViewDtoConverterService constructor() {
    // Global Field
    @Autowired lateinit var iotNodeService: IotNodeServiceV1

    // Node Entity List to Monitor Card Dto List
    fun convMonitorSummaryCards(listEntity: Collection<Node?>?): List<MonitorSummaryCardDto> {
        // Field
        val listCards: MutableList<MonitorSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: Node? in listEntity!!) {
            // Field
            var cardDto: MonitorSummaryCardDto
            var listItems: MutableList<MonitorCardItemDto?>
            var curDate: Date
            var lastReceive: Date?

            // Init
            cardDto = MonitorSummaryCardDto(entity)
            listItems = ArrayList()
            curDate = Date()
            lastReceive = null

            // Process
            for (nodeItem: NodeItem? in entity.getNodeItems()) {
                // Field
                var lastHistory: NodeItemHistory?

                // Init
                listItems.add(MonitorCardItemDto(nodeItem, iotNodeService!!.getLastNodeItemHistory(nodeItem)))
                lastHistory = iotNodeService.getLastNodeItemHistory(nodeItem)
                if (lastReceive == null || lastHistory.getReceiveDate().compareTo(lastReceive) > 0) lastReceive = lastHistory.getReceiveDate()
            }
            cardDto.setLastReceive(lastReceive)
            cardDto.setLastReceiveSecond((curDate.getTime() - lastReceive!!.getTime()) / 1000)

            // Add
            cardDto.setListItems(listItems)
            listCards.add(cardDto)
        }

        // Return
        return listCards
    }

    // NodeReserv Entity List to Reserv Card Dto List
    fun convReservSummaryCards(listEntity: Collection<NodeReserv?>?): List<ReservSummaryCardDto> {
        // Field
        val listCards: MutableList<ReservSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeReserv? in listEntity!!) listCards.add(ReservSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeAction Entity List to Reserv Action Card Dto List
    fun <T> convReservActionCards(listEntity: Collection<T>?): List<ReservActionCardDto> {
        // Field
        val listCards: MutableList<ReservActionCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: T in listEntity!!) if (entity is NodeAction) listCards.add(ReservActionCardDto(entity as NodeAction?)) else if (entity is NodeReservAction) listCards.add(ReservActionCardDto((entity as NodeReservAction).getNodeAction()))

        // Return
        return listCards
    }

    // NodeAction Entity List to Action Summary Card Dto List
    fun convActionSummaryCards(listEntity: Collection<NodeAction?>?): List<ActionSummaryCardDto> {
        // Field
        val listCards: MutableList<ActionSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeAction? in listEntity!!) listCards.add(ActionSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Action Item Card Dto List
    fun <T> convActionItemCards(listEntity: Collection<T>?): List<ActionItemCardDto> {
        // Field
        val listCards: MutableList<ActionItemCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: T in listEntity!!) if (entity is NodeItem) listCards.add(ActionItemCardDto(entity as NodeItem?)) else if (entity is NodeActionItem) listCards.add(ActionItemCardDto(entity as NodeActionItem?))

        // Return
        return listCards
    }

    // Node Entity List to Node Summary Card Dto List
    fun <T> convNodeSummaryCards(listEntity: Collection<T>?): List<NodeSummaryCardDto> {
        // Field
        val listCards: MutableList<NodeSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: T in listEntity!!) listCards.add(NodeSummaryCardDto(entity as Node?))

        // Return
        return listCards
    }

    // NodeItem Entity List to Node Item Card Dto List
    fun convNodeItemCards(listEntity: Collection<NodeItem?>): List<NodeItemCardDto> {
        // Field
        val listCards: MutableList<NodeItemCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeItem? in listEntity) listCards.add(NodeItemCardDto(entity, iotNodeService!!.getLastNodeItemHistory(entity)))

        // Return
        return listCards
    }

    // Member Entity List to Member Summary Card Dto List
    fun convMemberSummaryCards(listEntity: Collection<Member?>): List<MemberSummaryCardDto> {
        // Field
        val listCards: MutableList<MemberSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: Member? in listEntity) listCards.add(MemberSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberLoginLog Entity List to Member Activity Log Card Dto List
    fun convMemberActivityLogCards(listEntity: Collection<MemberLoginLog?>): List<MemberActivityLogCardDto> {
        // Field
        val listCards: MutableList<MemberActivityLogCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: MemberLoginLog? in listEntity) listCards.add(MemberActivityLogCardDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Node Manage Summary Card Dto List
    fun convNodeManageSummaryCards(listEntity: Collection<Node?>): List<NodeManageSummaryCardDto> {
        // Field
        val listCards: MutableList<NodeManageSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: Node? in listEntity) listCards.add(NodeManageSummaryCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Node Manage Item Card Dto List
    fun convNodeManageItemCards(listEntity: Collection<NodeItem?>): List<NodeManageItemCardDto> {
        // Field
        val listCards: MutableList<NodeManageItemCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeItem? in listEntity) listCards.add(NodeManageItemCardDto(entity))

        // Return
        return listCards
    }

    // NodeItem Entity List to Trigger Item Card Dto List
    fun convTriggerItemCards(listEntity: Collection<NodeItem?>): List<TriggerItemCardDto> {
        // Field
        val listCards: MutableList<TriggerItemCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeItem? in listEntity) listCards.add(TriggerItemCardDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Node Manage Summary Card Dto List
    fun convTriggerSummaryCards(listEntity: Collection<NodeTrigger?>?): List<TriggerSummaryCardDto> {
        // Field
        val listCards: MutableList<TriggerSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeTrigger? in listEntity!!) listCards.add(TriggerSummaryCardDto(entity))

        // Return
        return listCards
    }

    // Member Entity List to Group Member Card Item Dto List
    fun convGroupMemberCardItems(listEntity: Collection<Member?>?): List<GroupMemberCardItemDto> {
        // Field
        val listCards: MutableList<GroupMemberCardItemDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: Member? in listEntity!!) listCards.add(GroupMemberCardItemDto(entity))

        // Return
        return listCards
    }

    // Node Entity List to Group Node Card Item Dto List
    fun convGroupNodeCardItems(listEntity: Collection<Node?>?): List<GroupNodeCardItemDto> {
        // Field
        val listCards: MutableList<GroupNodeCardItemDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: Node? in listEntity!!) listCards.add(GroupNodeCardItemDto(entity))

        // Return
        return listCards
    }

    // NodeGroup Entity List to Node Group Summary Card Dto List
    fun convNodeGroupSummaryCards(listEntity: Collection<NodeGroup?>?): List<NodeGroupSummaryCardDto> {
        // Field
        val listCards: MutableList<NodeGroupSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: NodeGroup? in listEntity!!) listCards.add(NodeGroupSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberGroup Entity List to Member Group Summary Card Dto List
    fun convMemberGroupSummaryCards(listEntity: Collection<MemberGroup?>?): List<MemberGroupSummaryCardDto> {
        // Field
        val listCards: MutableList<MemberGroupSummaryCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: MemberGroup? in listEntity!!) listCards.add(MemberGroupSummaryCardDto(entity))

        // Return
        return listCards
    }

    // MemberInterlockToken Entity List to Interlock Token Card Dto List
    fun convInterlockTokenCards(listEntity: Collection<MemberInterlockToken?>?): List<InterlockTokenCardDto> {
        // Field
        val listCards: MutableList<InterlockTokenCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: MemberInterlockToken? in listEntity!!) listCards.add(InterlockTokenCardDto(entity))

        // Return
        return listCards
    }

    // MemberInterlockToken Entity List to Action Interlock Token Card Dto List
    fun convActionInterlockTokenCards(listEntity: Collection<MemberInterlockToken?>?): List<ActionInterlockCardDto> {
        // Field
        val listCards: MutableList<ActionInterlockCardDto>

        // Init
        listCards = ArrayList()

        // Process
        for (entity: MemberInterlockToken? in listEntity!!) listCards.add(ActionInterlockCardDto(entity))

        // Return
        return listCards
    }
}