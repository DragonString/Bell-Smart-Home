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
import net.softbell.bsh.dto.request.IotActionItemDto
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer
import javax.transaction.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@Service
class IotActionServiceV1 {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var nodeService: IotNodeServiceV1
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeActionRepo: NodeActionRepo
    @Autowired private lateinit var nodeActionItemRepo: NodeActionItemRepo

    fun getAvailableNodeItem(auth: Authentication?): List<NodeItem?> {
        // Field
        val listNodeItem: List<NodeItem?>

        // Init
        listNodeItem = nodeService.getCategoryNodeItems(auth!!, GroupRole.ACTION, ItemCategoryRule.CONTROL)

        // Return
        return listNodeItem
    }

    fun getAllNodeActions(auth: Authentication): List<NodeAction?> {
        // Field
        val member: Member?
        val listNodeAction: List<NodeAction?>

        // Init
        member = memberService.getMember(auth.name)

        // Exception
        listNodeAction = if (memberService.isAdmin(member)) nodeActionRepo.findAll() else nodeActionRepo.findByMember(member)

        // Return
        return listNodeAction
    }

    fun getNodeAction(auth: Authentication?, actionId: Long): NodeAction? {
        // Field
        val optNodeAction: Optional<NodeAction?>
        val nodeAction: NodeAction

        // Init
        optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return null

        // Load
        nodeAction = optNodeAction.get()

        // Return
        return nodeAction
    }

    @Transactional
    fun createAction(auth: Authentication, iotActionDto: IotActionDto): Boolean {
        // Log
        logger.info("Creating Action (" + iotActionDto.description + ")")

        // Field
        val member: Member?
        val nodeAction: NodeAction
        val mapActionItem: HashMap<Long, IotActionItemDto>?
        val listNodeActionItem: MutableList<NodeActionItem>
        val enableStatus: EnableStatusRule

        // Init
        member = memberService.getMember(auth.name)
        listNodeActionItem = ArrayList()
        mapActionItem = iotActionDto.mapActionItem
        enableStatus = if (iotActionDto.enableStatus) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (member == null) return false

        // Data Process - Action Info
        nodeAction = NodeAction(
                enableStatus = enableStatus,
                description = iotActionDto.description,
                member = member
        )

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long?, (_, itemId, itemStatus) ->
                if (itemId != 0L) {
                    // Field
                    val nodeActionItem: NodeActionItem

                    // Init
                    val optNodeItem: Optional<NodeItem?> = nodeItemRepo.findById(itemId)

                    // Build
                    if (optNodeItem.isPresent) {
                        nodeActionItem = NodeActionItem(
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
    fun modifyAction(auth: Authentication?, actionId: Long, iotActionDto: IotActionDto): Boolean {
        // Field
        val optNodeAction: Optional<NodeAction?>
        val nodeAction: NodeAction
        val mapActionItem: HashMap<Long, IotActionItemDto>?
        val listNodeActionItem: MutableList<NodeActionItem>
        val enableStatus: EnableStatusRule

        // Init
        optNodeAction = nodeActionRepo.findById(actionId)
        mapActionItem = iotActionDto.mapActionItem
        listNodeActionItem = ArrayList()
        enableStatus = if (iotActionDto.enableStatus) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (!optNodeAction.isPresent) return false

        // Load
        nodeAction = optNodeAction.get()
        //		listNodeActionItem = nodeAction.getNodeActionItems();

        // DB - Action Item Clear
        nodeActionItemRepo.deleteAll(nodeAction.nodeActionItems!!)
        nodeActionItemRepo.flush()
        //		nodeActionRepo.flush();
//		for (NodeActionItem value : listNodeActionItem)
//			nodeAction.removeNodeActionItem(value);

        // Data Process - Item Info
//        nodeAction.nodeActionItems = null // TODO 이거 뭐지?
        nodeAction.enableStatus = enableStatus
        nodeAction.description = iotActionDto.description

        // DB - Save
        nodeActionRepo.save(nodeAction)

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long?, (_, itemId, itemStatus) ->
                if (itemId != 0L) {
                    // Field
                    val optNodeItem: Optional<NodeItem?>
                    val nodeActionItem: NodeActionItem

                    // Init
                    optNodeItem = nodeItemRepo.findById(itemId)

                    // Build
                    if (optNodeItem.isPresent) {
                        nodeActionItem = NodeActionItem(
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
    fun deleteAction(auth: Authentication?, actionId: Long): Boolean {
        // Field
        val optNodeAction: Optional<NodeAction?>
        val nodeAction: NodeAction

        // Init
        optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return false

        // Load
        nodeAction = optNodeAction.get()

        // DB - Delete
        nodeActionItemRepo.deleteAll(nodeAction.nodeActionItems!!)
        nodeActionRepo.delete(nodeAction)

        // Return
        return true
    }

    fun execAction(actionId: Long, member: Member?): Boolean {
        // Field
        val optNodeAction: Optional<NodeAction?>
        var isSuccess = true

        // Init
        optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return false

        // Process
        isSuccess = execAction(optNodeAction.get(), member)

        // Return
        return isSuccess
    }

    fun execAction(actionId: Long, auth: Authentication): Boolean {
        // Field
        val member: Member?
        val optNodeAction: Optional<NodeAction?>
        var isSuccess = true

        // Init
        member = memberService.getMember(auth.name)
        optNodeAction = nodeActionRepo.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return false

        // Process
        isSuccess = execAction(optNodeAction.get(), member)

        // Return
        return isSuccess
    }

    fun execAction(nodeAction: NodeAction?, member: Member?): Boolean {
        // Field
        val listNodeActionItem: List<NodeActionItem>?
        var isSuccess = true

        // Exception
        if (nodeAction == null) return false

        // Permission
        if (!memberService.isAdmin(member)) // 관리자가 아닌데
            if (nodeAction.member != member) // 액션 제작자가 아니면
                return false // 수행 중단

        // Load
        listNodeActionItem = nodeAction.nodeActionItems

        // Process
        for (actionItem in listNodeActionItem!!) if (!nodeService.setItemValue(actionItem.nodeItem, actionItem.itemStatus!!)) isSuccess = false

        // Return
        return isSuccess
    }

    companion object : KLogging()
}