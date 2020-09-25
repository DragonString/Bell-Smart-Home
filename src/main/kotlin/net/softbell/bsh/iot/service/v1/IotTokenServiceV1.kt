package net.softbell.bsh.iot.service.v1

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
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Token 서비스
 */
@Service
class IotTokenServiceV1 {
    // Global Field
    @Autowired lateinit var iotTriggerService: IotTriggerServiceV1
    @Autowired lateinit var iotChannelCompV1: IotChannelCompV1
    @Autowired lateinit var iotAuthCompV1: IotAuthCompV1
    @Autowired lateinit var nodeRepo: NodeRepo
    @Autowired lateinit var nodeItemRepo: NodeItemRepo
    @Autowired lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo

    private fun getNormalTokenNode(token: String): Node? {
        // Field
        val node: Node?

        // Init
        node = nodeRepo!!.findByToken(token)

        // Exception
        if (node == null) // 토큰에 해당하는 노드가 없다면
        {
            // Field
            val data: BaseV1Dto

            // Init
            data = builder().sender("SERVER")
                    .target(token)
                    .cmd("INFO")
                    .type("CONNECTION")
                    .obj("NODE")
                    .value("FAIL")
                    .build()

            // Send
            iotChannelCompV1!!.sendDataToken(data) // 연결 실패 메시지 전송
            return null
        }
        return if (!iotAuthCompV1!!.isApprovalNode(node)) null else node

        // Return
    }

    @Transactional
    fun setNodeInfo(token: String, nodeInfo: NodeInfoV1Dto): Boolean {
        // Field
        val node: Node?

        // Init
        node = getNormalTokenNode(token)

        // Exception
        if (node == null) return false
        if (!node.getUid().equals(nodeInfo.getUid())) return false // 서버상 UID와 수신된 UID가 다르면 해킹으로 간주

        // Process
        node.setControlMode(nodeInfo.getControlMode())
        node.setNodeName(nodeInfo.getNodeName())
        node.setVersion(nodeInfo.getVersion())

        // DB - Save
        nodeRepo!!.save(node)

        // Log
        log.info(BellLog.getLogHead() + "Node Info Save (" + node.getUid() + ")")

        // Return
        return true
    }

    @Transactional
    fun setItemInfo(token: String, itemInfo: ItemInfoV1Dto): Boolean {
        // Field
        val node: Node?

        // Init
        node = getNormalTokenNode(token)

        // Exception
        if (node == null) return false

        // Process
        for (nodeItem in node.getNodeItems())  // TODO 불필요한 이 반복 부분 제거하고 NodeItemRepo에 조회절 추가하기
            if (nodeItem.getItemIndex() === itemInfo.getItemIndex()) // DB Update
            {
                nodeItem.setControlMode(itemInfo.getControlMode())
                nodeItem.setItemIndex(itemInfo.getItemIndex())
                nodeItem.setItemCategory(ItemCategoryRule.Companion.ofLegacyCode(itemInfo.getItemCategory()))
                nodeItem.setItemMode(ItemModeRule.Companion.ofLegacyCode(itemInfo.getItemMode()))
                nodeItem.setItemName(itemInfo.getItemName())
                nodeItem.setItemType(ItemTypeRule.Companion.ofLegacyCode(itemInfo.getItemType()))
                nodeItemRepo!!.save<NodeItem>(nodeItem)
                log.info(BellLog.getLogHead() + "Node Item Save (" + node.getUid() + "-" + nodeItem.getItemName() + ")")
                return true
            }

        // DB - Insert data
        val nodeItem: NodeItem = builder()
                .node(node)
                .controlMode(itemInfo.getControlMode())
                .itemIndex(itemInfo.getItemIndex())
                .itemCategory(ItemCategoryRule.Companion.ofLegacyCode(itemInfo.getItemCategory()))
                .itemMode(ItemModeRule.Companion.ofLegacyCode(itemInfo.getItemMode()))
                .itemName(itemInfo.getItemName())
                .itemType(ItemTypeRule.Companion.ofLegacyCode(itemInfo.getItemType()))
                .alias(itemInfo.getItemName())
                .build()
        nodeItemRepo!!.save(nodeItem)

        // Log
        log.info(BellLog.getLogHead() + "Node New Item Save (" + node.getUid() + "-" + nodeItem.getItemName() + ")")

        // Return
        return true
    }

    fun reqItemValue(token: String?, pin: Int) {
        // Field
        val data: BaseV1Dto

        // Init
        data = builder().sender("SERVER")
                .target(token)
                .cmd("GET")
                .type("VALUE")
                .obj("ITEM")
                .value(pin)
                .build()

        // Send
        iotChannelCompV1!!.sendDataToken(data)
    }

    @Transactional
    fun setItemValue(token: String, itemValue: ItemValueV1Dto): Boolean {
        // Field
        val node: Node?
        val nodeItem: NodeItem?
        val nodeItemHistory: NodeItemHistory

        // Init
        node = getNormalTokenNode(token) // TODO 이것도 token, uid로 한번에 검색되게

        // Exception
        if (node == null) return false
        if (itemValue.getItemStatus() == null) {
            log.error(BellLog.getLogHead() + "ItemStatus 수신 데이터 없음 (" + node.getAlias() + ")")
            return false
        }
        nodeItem = nodeItemRepo!!.findByNodeAndItemIndex(node, itemValue.getItemIndex())
        if (nodeItem == null) return false

        // Process
        nodeItemHistory = builder()
                .nodeItem(nodeItem)
                .itemStatus(itemValue.getItemStatus())
                .receiveDate(Date())
                .build()

        // DB - Save
        nodeItemHistoryRepo!!.save(nodeItemHistory)

        // Trigger Check
        iotTriggerService!!.procTrigger(nodeItem)

        // Return
        return true
    }
}