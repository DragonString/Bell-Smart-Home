package net.softbell.bsh.iot.service.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroupItem
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node 서비스
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
    fun getAllNodes(): List<Node?> {
        return nodeRepo.findAll()
    }

    @Deprecated("")
    fun getAllNodes(intPage: Int, intCount: Int): Page<Node?>? {
        // Field
        val curPage: Pageable

        // Init
        curPage = PageRequest.of(intPage - 1, intCount)

        // Return
        return nodeRepo.findAll(curPage)
    }

    @Deprecated("")
    fun getAllNodeItems(auth: Authentication?): List<NodeItem?>? {
        // Field
        // TODO 회원 권한에 맞는 아이템만 조회하는 기능 추가 필요... 언젠가는... 하겠지.... 권한... 보안... 으어...

        // Init


        // Return
        return nodeItemRepo.findAll()
    }

    fun getAllNodes(auth: Authentication, role: GroupRole?): List<Node?> {
        // Exception
        if (memberService.getAdminMember(auth.name) != null) // 관리자면
            return nodeRepo.findAll() // 모든 노드 반환

        // Field
        val listNode: List<Node?>
        val listNodeGroupItem: List<NodeGroupItem?>?

        // Init
        listNodeGroupItem = permissionService.getPrivilegeNodeGroupItems(role, auth)

        // Process
        listNode = nodeRepo.findByNodeGroupItemsIn(listNodeGroupItem)

        // Return
        return listNode
    }

    fun getAllNodeItems(auth: Authentication, role: GroupRole?): List<NodeItem?>? {
        // Exception
        if (memberService.getAdminMember(auth.name) != null) return nodeItemRepo.findAll()

        // Field
        val listNodeItem: List<NodeItem?>?
        val listNode: List<Node?>?

        // Init
        listNode = getAllNodes(auth, role)

        // Process
        listNodeItem = nodeItemRepo.findByNodeIn(listNode)

        // Return
        return listNodeItem
    }

    fun getCategoryNodeItems(auth: Authentication, role: GroupRole?, itemCategory: ItemCategoryRule?): List<NodeItem?> {
        // Exception
        if (memberService.getAdminMember(auth.name) != null) return nodeItemRepo.findByItemCategory(itemCategory)

        // Field
        val listNodeItem: List<NodeItem?>
        val listNode: List<Node?>

        // Init
        listNode = getAllNodes(auth, role)

        // Process
        listNodeItem = nodeItemRepo.findByNodeInAndItemCategory(listNode, itemCategory)

        // Return
        return listNodeItem
    }

    fun getNode(nodeId: Long): Node? {
        // Field
        val optNode: Optional<Node?>

        // Init
        optNode = nodeRepo.findById(nodeId)

        // Return
        return if (optNode.isPresent) optNode.get() else null
    }

    fun getNodeItem(itemId: Long): NodeItem? {
        // Field
        val optNodeItem: Optional<NodeItem?>

        // Init
        optNodeItem = nodeItemRepo.findById(itemId)

        // Return
        return if (optNodeItem.isPresent) optNodeItem.get() else null
    }

    fun getLastNodeItemHistory(nodeItem: NodeItem?): NodeItemHistory? {
        // Process
        return nodeItemHistoryRepo.findFirstByNodeItemOrderByItemHistoryIdDesc(nodeItem)
    }

    fun getLastNodeItemHistory(nodeItemId: Long): NodeItemHistory? {
        // Field
        val optNodeItem: Optional<NodeItem?>

        // Init
        optNodeItem = nodeItemRepo.findById(nodeItemId)

        // Exception
        return if (!optNodeItem.isPresent) null else nodeItemHistoryRepo.findFirstByNodeItemOrderByItemHistoryIdDesc(optNodeItem.get())

        // Process
    }

    fun getNodeItemHistory(nodeItemId: Long): List<NodeItemHistory?>? {
        // Field
        val listNodeItemHistory: List<NodeItemHistory?>?
        val curPage: Pageable
        val optNodeItem: Optional<NodeItem?>

        // Init
        optNodeItem = nodeItemRepo.findById(nodeItemId)

        // Exception
        if (!optNodeItem.isPresent) return null

        // Process
        curPage = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "itemHistoryId")) // TODO 임시로 50개 뽑게 설정함
        listNodeItemHistory = nodeItemHistoryRepo.findByNodeItem(optNodeItem.get(), curPage)
        Collections.reverse(listNodeItemHistory)

        // Return
        return listNodeItemHistory
    }

    fun setItemValue(nodeItem: NodeItem?, itemValue: Double): Boolean {
        // Field
        val baseMessage: BaseV1Dto?
        //		BaseV1Dto getValueMessage;
        val itemValueData: ItemValueV1Dto
        val token: String?
        val pinId: Byte

        // Exception
        if (nodeItem == null) return false

        // Process
        token = nodeItem.node!!.token
        pinId = nodeItem.itemIndex!!
        itemValueData = ItemValueV1Dto(
                itemIndex = pinId,
                itemStatus = itemValue
        )

        baseMessage = iotMessageService.getBaseMessage(token!!, "SET", "VALUE", "ITEM", itemValueData)
        //		getValueMessage = iotMessageService.getBaseMessage(token, "GET", "VALUE", "ITEM", pinId);

        // Send
        iotChannelCompV1.sendDataToken(baseMessage!!)
        //		iotChannelCompV1.sendDataToken(getValueMessage);

        // Return
        return true
    }

    fun setNodeEnableStatus(nodeId: Long, enableStatusRule: EnableStatusRule): Boolean {
        // Field
        val optNode: Optional<Node?>
        val node: Node

        // Init
        optNode = nodeRepo.findById(nodeId)

        // Exception
        node = if (!optNode.isPresent) return false else optNode.get()
        if (node.enableStatus === enableStatusRule) return false

        // Process
        if (node.enableStatus === EnableStatusRule.WAIT && enableStatusRule !== EnableStatusRule.REJECT) node.approvalDate = Date()
        node.enableStatus = enableStatusRule

        // DB - Update
        nodeRepo.save(node)

        // Return
        return true
    }

    fun setNodeAlias(nodeId: Long, alias: String?): Boolean {
        // Field
        val node: Node?

        // Init
        node = getNode(nodeId)

        // Exception
        if (node == null) return false

        // Process
        node.alias = alias

        // DB - Update
        nodeRepo.save(node)

        // Return
        return true
    }

    fun setNodeItemAlias(nodeItemId: Long, alias: String?): Boolean {
        // Field
        val nodeItem: NodeItem?

        // Init
        nodeItem = getNodeItem(nodeItemId)

        // Exception
        if (nodeItem == null) return false

        // Process
        nodeItem.alias = alias

        // DB - Update
        nodeItemRepo.save(nodeItem)

        // Return
        return true
    }

    fun restartNode(node: Node?): Boolean {
        // Field
        val baseMessage: BaseV1Dto?
        val token: String?

        // Exception
        if (node == null) return false

        // Process
        token = node.token
        baseMessage = iotMessageService.getBaseMessage(token!!, "ACT", "SYS", "RESTART", "NOW")

        // Send
        iotChannelCompV1.sendDataToken(baseMessage!!)

        // Return
        return true
    }
}