package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.ItemCategoryRule
import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT Token 서비스
 */
@Service
class IotTokenServiceV1 {
    // Global Field
    @Autowired private lateinit var iotTriggerService: IotTriggerServiceV1
    @Autowired private lateinit var iotChannelCompV1: IotChannelCompV1
    @Autowired private lateinit var iotAuthCompV1: IotAuthCompV1
    @Autowired private lateinit var nodeRepo: NodeRepo
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo

    private fun getNormalTokenNode(token: String): Node? {
        // Init
        val node = nodeRepo.findByToken(token)

        // Exception
        if (node == null) // 토큰에 해당하는 노드가 없다면
        {
            // Field
            val data: BaseV1Dto = BaseV1Dto(
                    sender = "SERVER",
                    target = token,
                    cmd = "INFO",
                    type = "CONNECTION",
                    obj = "NODE",
                    value = "FAIL"
            )

            // Send
            iotChannelCompV1.sendDataToken(data) // 연결 실패 메시지 전송
            return null
        }

        // Return
        return if (iotAuthCompV1.isApprovalNode(node))
            node
        else
            null
    }

    @Transactional
    fun setNodeInfo(token: String, nodeInfo: NodeInfoV1Dto): Boolean {
        // Init
        val node = getNormalTokenNode(token) ?: return false

        // Exception
        if (node.uid != nodeInfo.uid)
            return false // 서버상 UID와 수신된 UID가 다르면 해킹으로 간주

        // Process
        node.controlMode = nodeInfo.controlMode
        node.nodeName = nodeInfo.nodeName
        node.version = nodeInfo.version

        // DB - Save
        nodeRepo.save(node)

        // Log
        logger.info("Node Info Save (" + node.uid + ")")

        // Return
        return true
    }

    @Transactional
    fun setItemInfo(token: String, itemInfo: ItemInfoV1Dto): Boolean {
        // Init
        val node = getNormalTokenNode(token) ?: return false

        // Process
        for (nodeItem in node.nodeItems)  // TODO 불필요한 이 반복 부분 제거하고 NodeItemRepo에 조회절 추가하기
            if (nodeItem.itemIndex == itemInfo.itemIndex) // DB Update
            {
                nodeItem.controlMode = itemInfo.controlMode
                nodeItem.itemIndex = itemInfo.itemIndex
                nodeItem.itemCategory = ItemCategoryRule.ofLegacyCode(itemInfo.itemCategory)
                nodeItem.itemMode = ItemModeRule.ofLegacyCode(itemInfo.itemMode)
                nodeItem.itemName = itemInfo.itemName
                nodeItem.itemType = ItemTypeRule.ofLegacyCode(itemInfo.itemType)

                nodeItemRepo.save(nodeItem)
                logger.info("Node Item Save (" + node.uid + "-" + nodeItem.itemName + ")")
                return true
            }

        // DB - Insert data
        val nodeItem = NodeItem(
                node = node,
                controlMode = itemInfo.controlMode,
                itemIndex = itemInfo.itemIndex,
                itemCategory = ItemCategoryRule.ofLegacyCode(itemInfo.itemCategory),
                itemMode = ItemModeRule.ofLegacyCode(itemInfo.itemMode),
                itemName = itemInfo.itemName,
                itemType = ItemTypeRule.ofLegacyCode(itemInfo.itemType),
                alias = itemInfo.itemName
        )

        nodeItemRepo.save(nodeItem)

        // Log
        logger.info("Node New Item Save (" + node.uid + "-" + nodeItem.itemName + ")")

        // Return
        return true
    }

    fun reqItemValue(token: String, pin: Int) {
        // Init
        val data = BaseV1Dto(
                sender = "SERVER",
                target = token,
                cmd = "GET",
                type = "VALUE",
                obj = "ITEM",
                value = pin
        )

        // Send
        iotChannelCompV1.sendDataToken(data)
    }

    @Transactional
    fun setItemValue(token: String, itemValue: ItemValueV1Dto): Boolean {
        // Init
        val node = getNormalTokenNode(token) ?: return false // TODO 이것도 token, uid로 한번에 검색되게

        // Exception
        if (itemValue.itemStatus == null) {
            logger.error("ItemStatus 수신 데이터 없음 (" + node.alias + ")")
            return false
        }
        val nodeItem = nodeItemRepo.findByNodeAndItemIndex(node, itemValue.itemIndex) ?: return false

        // Process
        val nodeItemHistory: NodeItemHistory = NodeItemHistory(
                nodeItem = nodeItem,
                itemStatus = itemValue.itemStatus!!,
                receiveDate = Date()
        )

        // DB - Save
        nodeItemHistoryRepo.save(nodeItemHistory)

        // Trigger Check
        iotTriggerService.procTrigger(nodeItem)

        // Return
        return true
    }

    companion object : KLogging()
}