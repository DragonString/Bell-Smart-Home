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
import org.springframework.security.core.Authentication
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

    fun getAvailableNodeItem(auth: Authentication): List<NodeItem> {
        // Return
        return nodeService.getCategoryNodeItems(auth, GroupRole.ACTION, ItemCategoryRule.CONTROL)
    }

    fun getAllNodeActions(auth: Authentication): List<NodeAction> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return if (memberService.isAdmin(member))
            nodeActionRepo.findAll()
        else
            nodeActionRepo.findByMember(member)
    }

    fun getNodeAction(auth: Authentication, actionId: Long): NodeAction? {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return null

        // Return
        return optNodeAction.get()
    }

    @Transactional
    fun createAction(auth: Authentication, iotActionDto: IotActionDto): Boolean {
        // Log
        logger.info("Creating Action (" + iotActionDto.description + ")")

        // Init
        val member = memberService.getMember(auth.name)
        val listNodeActionItem: MutableList<NodeActionItem> = ArrayList()
        val mapActionItem = iotActionDto.mapActionItem
        val enableStatus = if (iotActionDto.enableStatus)
            EnableStatusRule.ENABLE
        else
            EnableStatusRule.DISABLE

        // Exception
        if (member == null)
            return false

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
    fun modifyAction(auth: Authentication, actionId: Long, iotActionDto: IotActionDto): Boolean {
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

    @Transactional
    fun deleteAction(auth: Authentication, actionId: Long): Boolean {
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

    fun execAction(actionId: Long, member: Member): Boolean {
        // Init
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return false

        // Return
        return execAction(optNodeAction.get(), member)
    }

    fun execAction(actionId: Long, auth: Authentication): Boolean {
        // Init
        val member = memberService.getMember(auth.name) ?: return false
        val optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent)
            return false

        // Return
        return execAction(optNodeAction.get(), member)
    }

    fun execAction(nodeAction: NodeAction, member: Member): Boolean {
        // Init
        var isSuccess = true

        // Permission
        if (!memberService.isAdmin(member)) // 관리자가 아닌데
            if (nodeAction.member != member) // 액션 제작자가 아니면
                return false // 수행 중단

        // Load
        val listNodeActionItem = nodeAction.nodeActionItems

        // Process
        for (actionItem in listNodeActionItem)
            if (!nodeService.setItemValue(actionItem.nodeItem, actionItem.itemStatus))
                isSuccess = false

        // Return
        return isSuccess
    }

    companion object : KLogging()
}