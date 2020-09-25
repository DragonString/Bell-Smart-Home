package net.softbell.bsh.iot.service.v1

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
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var nodeService: IotNodeServiceV1
    @Autowired lateinit var nodeItemRepo: NodeItemRepo
    @Autowired lateinit var nodeActionRepo: NodeActionRepo
    @Autowired lateinit var nodeActionItemRepo: NodeActionItemRepo

    fun getAvailableNodeItem(auth: Authentication): List<NodeItem?>? {
        // Field
        val listNodeItem: List<NodeItem?>?

        // Init
        listNodeItem = nodeService!!.getCategoryNodeItems(auth, GroupRole.ACTION, ItemCategoryRule.CONTROL)

        // Return
        return listNodeItem
    }

    fun getAllNodeActions(auth: Authentication): List<NodeAction?>? {
        // Field
        val member: Member?
        val listNodeAction: List<NodeAction?>?

        // Init
        member = memberService!!.getMember(auth.name)

        // Exception
        listNodeAction = if (memberService.isAdmin(member)) nodeActionRepo!!.findAll() else nodeActionRepo!!.findByMember(member)

        // Return
        return listNodeAction
    }

    fun getNodeAction(auth: Authentication?, actionId: Long): NodeAction? {
        // Field
        val optNodeAction: Optional<NodeAction?>
        val nodeAction: NodeAction

        // Init
        optNodeAction = nodeActionRepo!!.findById(actionId)

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
        log.info(BellLog.getLogHead() + "Creating Action (" + iotActionDto.getDescription() + ")")

        // Field
        val member: Member?
        val nodeAction: NodeAction
        val mapActionItem: HashMap<Long, IotActionItemDto>?
        val listNodeActionItem: MutableList<NodeActionItem>
        val enableStatus: EnableStatusRule

        // Init
        member = memberService!!.getMember(auth.name)
        listNodeActionItem = ArrayList()
        mapActionItem = iotActionDto.getMapActionItem()
        enableStatus = if (iotActionDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (member == null) return false

        // Data Process - Action Info
        nodeAction = builder()
                .enableStatus(enableStatus)
                .description(iotActionDto.getDescription())
                .member(member)
                .build()

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long?, value: IotActionItemDto ->
                if (value.getItemId() != null && value.getItemId() !== 0) {
                    // Field
                    val optNodeItem: Optional<NodeItem?>
                    val nodeActionItem: NodeActionItem

                    // Init
                    optNodeItem = nodeItemRepo!!.findById(value.getItemId())

                    // Build
                    if (optNodeItem.isPresent) {
                        nodeActionItem = builder()
                                .nodeItem(optNodeItem.get())
                                .itemStatus(value.getItemStatus())
                                .nodeAction(nodeAction)
                                .build()

                        // List Add
                        listNodeActionItem.add(nodeActionItem)
                    }
                }
            })
            nodeAction.setNodeActionItems(listNodeActionItem)
        }

        // DB - Save
        nodeActionRepo!!.save(nodeAction)
        nodeActionItemRepo!!.saveAll(listNodeActionItem)

        // Log
        log.info(BellLog.getLogHead() + "Created Action (" + nodeAction.getActionId() + ", " + iotActionDto.getDescription() + ")")

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
        optNodeAction = nodeActionRepo!!.findById(actionId)
        mapActionItem = iotActionDto.getMapActionItem()
        listNodeActionItem = ArrayList()
        enableStatus = if (iotActionDto.isEnableStatus()) EnableStatusRule.ENABLE else EnableStatusRule.DISABLE

        // Exception
        if (!optNodeAction.isPresent) return false

        // Load
        nodeAction = optNodeAction.get()
        //		listNodeActionItem = nodeAction.getNodeActionItems();

        // DB - Action Item Clear
        nodeActionItemRepo!!.deleteAll(nodeAction.getNodeActionItems())
        nodeActionItemRepo.flush()
        //		nodeActionRepo.flush();
//		for (NodeActionItem value : listNodeActionItem)
//			nodeAction.removeNodeActionItem(value);

        // Data Process - Item Info
        nodeAction.setNodeActionItems(null)
        nodeAction.setEnableStatus(enableStatus)
        nodeAction.setDescription(iotActionDto.getDescription())

        // DB - Save
        nodeActionRepo.save(nodeAction)

        // Data Process - Action Item Info
        if (mapActionItem != null) {
            mapActionItem.forEach(BiConsumer { key: Long?, value: IotActionItemDto ->
                if (value.getItemId() != null && value.getItemId() !== 0) {
                    // Field
                    val optNodeItem: Optional<NodeItem?>
                    val nodeActionItem: NodeActionItem

                    // Init
                    optNodeItem = nodeItemRepo!!.findById(value.getItemId())

                    // Build
                    if (optNodeItem.isPresent) {
                        nodeActionItem = builder()
                                .nodeItem(optNodeItem.get())
                                .itemStatus(value.getItemStatus())
                                .nodeAction(nodeAction)
                                .build()

                        // List Add
                        listNodeActionItem.add(nodeActionItem)
                    }
                }
            })
            nodeAction.setNodeActionItems(listNodeActionItem)
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
        optNodeAction = nodeActionRepo!!.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return false

        // Load
        nodeAction = optNodeAction.get()

        // DB - Delete
        nodeActionItemRepo!!.deleteAll(nodeAction.getNodeActionItems())
        nodeActionRepo.delete(nodeAction)

        // Return
        return true
    }

    fun execAction(actionId: Long, member: Member?): Boolean {
        // Field
        val optNodeAction: Optional<NodeAction?>
        var isSuccess = true

        // Init
        optNodeAction = nodeActionRepo!!.findById(actionId)

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
        member = memberService!!.getMember(auth.name)
        optNodeAction = nodeActionRepo!!.findById(actionId)

        // Exception
        if (!optNodeAction.isPresent) return false

        // Process
        isSuccess = execAction(optNodeAction.get(), member)

        // Return
        return isSuccess
    }

    fun execAction(nodeAction: NodeAction?, member: Member?): Boolean {
        // Field
        val listNodeActionItem: List<NodeActionItem>
        var isSuccess = true

        // Exception
        if (nodeAction == null) return false

        // Permission
        if (!memberService!!.isAdmin(member)) // 관리자가 아닌데
            if (!nodeAction.getMember().equals(member)) // 액션 제작자가 아니면
                return false // 수행 중단

        // Load
        listNodeActionItem = nodeAction.getNodeActionItems()

        // Process
        for (actionItem in listNodeActionItem) if (!nodeService!!.setItemValue(actionItem.getNodeItem(), actionItem.getItemStatus())) isSuccess = false

        // Return
        return isSuccess
    }
}