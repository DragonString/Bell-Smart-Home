package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeActionItem
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.repository.NodeActionItemRepo
import net.softbell.bsh.domain.repository.NodeActionRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.dto.request.IotActionDto
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT Node Action 서비스
 */
@Service
class IotActionServiceV1 {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var nodeService: IotNodeServiceV1
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeActionRepo: NodeActionRepo
    @Autowired private lateinit var nodeActionItemRepo: NodeActionItemRepo

    fun getAvailableNodeItem(member: Member): List<NodeItem> {
        // Return
        if (memberService.isAdmin(member))
            return nodeService.getCategoryAllNodeItems(ItemCategoryRule.CONTROL)
        else
            return nodeService.getCategoryPrivilegesNodeItems(member, GroupRole.ACTION, ItemCategoryRule.CONTROL)
    }

    fun getNodeActions(): List<NodeAction> {
        // Return
        return nodeActionRepo.findAll()
    }

    fun getPrivilegesNodeActions(member: Member): List<NodeAction> {
        if (memberService.isAdmin(member))
            return getNodeActions()

        // Return
        return nodeActionRepo.findByMember(member)
    }

    fun getNodeAction(actionId: Long): NodeAction? {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return null

        // Return
        return optNodeAction.get()
    }

    fun getPrivilegesNodeAction(member: Member, actionId: Long): NodeAction? {
        // TODO 권한 있는 노드 액션만 반환 필요
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return null

        // Return
        return optNodeAction.get()
    }

    @Transactional
    fun createAction(member: Member, iotActionDto: IotActionDto): Boolean {
        // Log
        logger.info("Creating Action (" + iotActionDto.description + ")")

        // Init
        val listNodeActionItem: MutableList<NodeActionItem> = ArrayList()
        val mapActionItem = iotActionDto.mapActionItem
        val enableStatus = if (iotActionDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Data Process - Action Info
        val nodeAction = NodeAction(
                enableStatus = enableStatus,
                description = iotActionDto.description,
                member = member
        )

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long, (_, itemId, itemStatus) ->
                if (itemId != -1L) {
                    // Init
                    val optNodeItem = nodeItemRepo.findById(itemId)

                    // Build
                    if (optNodeItem.isPresent) {
                        val nodeActionItem = NodeActionItem(
                                nodeItem = optNodeItem.get(),
                                itemStatus = itemStatus,
                                nodeAction = nodeAction
                        )

                        // List Add
                        listNodeActionItem.add(nodeActionItem)
                    }
                }
            })
            nodeAction.nodeActionItems = listNodeActionItem
        }

        // DB - Save
        nodeActionRepo.save(nodeAction)
        nodeActionItemRepo.saveAll(listNodeActionItem)

        // Log
        logger.info("Created Action (" + nodeAction.actionId + ", " + iotActionDto.description + ")")

        // Return
        return true
    }

    @Transactional
    fun modifyAction(actionId: Long, iotActionDto: IotActionDto): Boolean {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)
        val mapActionItem = iotActionDto.mapActionItem
        val listNodeActionItem: MutableList<NodeActionItem> = ArrayList()
        val enableStatus = if (iotActionDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Exception
        if (!optNodeAction.isPresent)
            return false

        // Load
        val nodeAction = optNodeAction.get()
        //		listNodeActionItem = nodeAction.getNodeActionItems();

        // DB - Action Item Clear
        nodeActionItemRepo.deleteAll(nodeAction.nodeActionItems)
        nodeActionItemRepo.flush()
        //		nodeActionRepo.flush();
//		for (NodeActionItem value : listNodeActionItem)
//			nodeAction.removeNodeActionItem(value);

        // Data Process - Item Info
//        nodeAction.nodeActionItems = null // TODO 이거 뭐지?
        nodeAction.enableStatus = enableStatus
        nodeAction.description = iotActionDto.description

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long?, (_, itemId, itemStatus) ->
                if (itemId != -1L) {
                    // Init
                    val optNodeItem = nodeItemRepo.findById(itemId)

                    // Build
                    if (optNodeItem.isPresent) {
                        val nodeActionItem = NodeActionItem(
                                nodeItem = optNodeItem.get(),
                                itemStatus = itemStatus,
                                nodeAction = nodeAction
                        )

                        // List Add
                        listNodeActionItem.add(nodeActionItem)
                    }
                }
            })
            nodeAction.nodeActionItems = listNodeActionItem
        }

        // DB - Update
//		nodeActionRepo.save(nodeAction);
        nodeActionItemRepo.saveAll(listNodeActionItem)

        // Return
        return true
    }

    fun modifyPrivilegesAction(member: Member, actionId: Long, iotActionDto: IotActionDto): Boolean {
        // TODO 권한 체크 필요
        return modifyAction(actionId, iotActionDto)
    }

    @Transactional
    fun deleteAction(actionId: Long): Boolean {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return false

        // Load
        val nodeAction = optNodeAction.get()

        // DB - Delete
        nodeActionItemRepo.deleteAll(nodeAction.nodeActionItems)
        nodeActionRepo.delete(nodeAction)

        // Return
        return true
    }

    fun deletePrivilegesAction(member: Member, actionId: Long): Boolean {
        // TODO 권한 체크 필요
        return deleteAction(actionId)
    }

    fun execAction(nodeAction: NodeAction): Boolean {
        // Init
        var isSuccess = true

        // Load
        val listNodeActionItem = nodeAction.nodeActionItems

        // Process
        for (actionItem in listNodeActionItem)
            if (!nodeService.setItemValue(actionItem.nodeItem, actionItem.itemStatus))
                isSuccess = false

        // Return
        return isSuccess
    }

    fun execPrivilegesAction(actionId: Long, member: Member): Boolean {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return false

        // Return
        return execPrivilegesAction(optNodeAction.get(), member)
    }

    fun execPrivilegesAction(nodeAction: NodeAction, member: Member): Boolean {
        // Permission
        if (!memberService.isAdmin(member)) // 관리자가 아닌데
            if (nodeAction.member != member) // 액션 제작자가 아니면
                return false // 수행 중단

        // Return
        return execAction(nodeAction)
    }

    companion object : KLogging()
}