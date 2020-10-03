package net.softbell.bsh.iot.service.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT Node 서비스
 */
@Service
class IotNodeServiceV1 {
    // Global Field
    @Autowired private lateinit var iotMessageService: IotMessageServiceV1
    @Autowired private lateinit var permissionService: PermissionService
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var iotChannelCompV1: IotChannelCompV1
    @Autowired private lateinit var nodeRepo: NodeRepo
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo

    @Deprecated("")
    fun getAllNodes(): List<Node> {
        return nodeRepo.findAll()
    }

    @Deprecated("")
    fun getAllNodes(intPage: Int, intCount: Int): Page<Node> {
        // Init
        val curPage = PageRequest.of(intPage - 1, intCount)

        // Return
        return nodeRepo.findAll(curPage)
    }

    @Deprecated("")
    fun getAllNodeItems(auth: Authentication): List<NodeItem> {
        // Field
        // TODO 회원 권한에 맞는 아이템만 조회하는 기능 추가 필요... 언젠가는... 하겠지.... 권한... 보안... 으어...

        // Init


        // Return
        return nodeItemRepo.findAll()
    }

    fun getAllNodes(auth: Authentication, role: GroupRole): List<Node> {
        // Exception
        if (memberService.getAdminMember(auth.name) != null) // 관리자면
            return nodeRepo.findAll() // 모든 노드 반환

        // Init
        val listNodeGroupItem = permissionService.getPrivilegeNodeGroupItems(role, auth)

        // Return
        return nodeRepo.findByNodeGroupItemsIn(listNodeGroupItem)
    }

    fun getAllNodeItems(auth: Authentication, role: GroupRole): List<NodeItem> {
        // Exception
        if (memberService.getAdminMember(auth.name) != null)
            return nodeItemRepo.findAll()

        // Init
        val listNode = getAllNodes(auth, role)

        // Return
        return nodeItemRepo.findByNodeIn(listNode)
    }

    fun getCategoryNodeItems(auth: Authentication, role: GroupRole, itemCategory: ItemCategoryRule): List<NodeItem> {
        // Exception
        if (memberService.getAdminMember(auth.name) != null)
            return nodeItemRepo.findByItemCategory(itemCategory)

        // Init
        val listNode = getAllNodes(auth, role)

        // Return
        return nodeItemRepo.findByNodeInAndItemCategory(listNode, itemCategory)
    }

    fun getNode(nodeId: Long): Node? {
        // Init
        val optNode = nodeRepo.findById(nodeId)

        // Return
        return if (optNode.isPresent)
            optNode.get()
        else
            null
    }

    fun getNodeItem(itemId: Long): NodeItem? {
        // Init
        val optNodeItem = nodeItemRepo.findById(itemId)

        // Return
        return if (optNodeItem.isPresent)
            optNodeItem.get()
        else
            null
    }

    fun getLastNodeItemHistory(nodeItem: NodeItem): NodeItemHistory? {
        // Process
        return nodeItemHistoryRepo.findFirstByNodeItemOrderByItemHistoryIdDesc(nodeItem)
    }

    fun getLastNodeItemHistory(nodeItemId: Long): NodeItemHistory? {
        // Init
        val optNodeItem = nodeItemRepo.findById(nodeItemId)

        // Return
        return if (optNodeItem.isPresent)
            nodeItemHistoryRepo.findFirstByNodeItemOrderByItemHistoryIdDesc(optNodeItem.get())
        else
            null
    }

    fun getNodeItemHistory(nodeItemId: Long): List<NodeItemHistory> {
        // Init
        val optNodeItem = nodeItemRepo.findById(nodeItemId)

        // Exception
        if (!optNodeItem.isPresent)
            return emptyList()

        // Process
        val curPage = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "itemHistoryId")) // TODO 임시로 50개 뽑게 설정함
        val listNodeItemHistory = nodeItemHistoryRepo.findByNodeItem(optNodeItem.get(), curPage)
        Collections.reverse(listNodeItemHistory)

        // Return
        return listNodeItemHistory
    }

    fun setItemValue(nodeItem: NodeItem, itemValue: Double): Boolean {
        // Process
        val token = nodeItem.node.token ?: return false
        val pinId = nodeItem.itemIndex
        val itemValueData = ItemValueV1Dto(
                itemIndex = pinId,
                itemStatus = itemValue
        )

        val baseMessage = iotMessageService.getBaseMessage(token, "SET", "VALUE", "ITEM", itemValueData)
        //		getValueMessage = iotMessageService.getBaseMessage(token, "GET", "VALUE", "ITEM", pinId);

        // Send
        iotChannelCompV1.sendDataToken(baseMessage)
        //		iotChannelCompV1.sendDataToken(getValueMessage);

        // Return
        return true
    }

    fun setNodeEnableStatus(nodeId: Long, enableStatusRule: EnableStatusRule): Boolean {
        // Init
        val optNode = nodeRepo.findById(nodeId)

        // Exception
        val node = if (!optNode.isPresent)
            return false
        else
            optNode.get()
        if (node.enableStatus == enableStatusRule)
            return false

        // Process
        if (node.enableStatus == EnableStatusRule.WAIT && enableStatusRule != EnableStatusRule.REJECT)
            node.approvalDate = Date()
        node.enableStatus = enableStatusRule

        // DB - Update
        nodeRepo.save(node)

        // Return
        return true
    }

    fun setNodeAlias(nodeId: Long, alias: String): Boolean {
        // Init
        val node = getNode(nodeId) ?: return false

        // Process
        node.alias = alias

        // DB - Update
        nodeRepo.save(node)

        // Return
        return true
    }

    fun setNodeItemAlias(nodeItemId: Long, alias: String): Boolean {
        // Init
        val nodeItem = getNodeItem(nodeItemId) ?: return false

        // Process
        nodeItem.alias = alias

        // DB - Update
        nodeItemRepo.save(nodeItem)

        // Return
        return true
    }

    fun restartNode(node: Node): Boolean {
        // Process
        val token = node.token
        val baseMessage = iotMessageService.getBaseMessage(token!!, "ACT", "SYS", "RESTART", "NOW")

        // Send
        iotChannelCompV1.sendDataToken(baseMessage)

        // Return
        return true
    }
}