package net.softbell.bsh.controller.view.advance

import lombok.AllArgsConstructor
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import net.softbell.bsh.dto.view.advance.NodeInfoCardDto
import net.softbell.bsh.dto.view.advance.NodeItemHistoryCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/node")
class NodeView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/advance"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"
    private val viewDtoConverterService: ViewDtoConverterService? = null
    private val iotNodeService: IotNodeServiceV1? = null
    private val centerService: CenterService? = null
    @GetMapping
    fun dispIndex(model: Model, auth: Authentication,
                  @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                  @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val listNode: List<Node?>?

        // Init
        listNode = iotNodeService!!.getAllNodes(auth, GroupRole.MANUAL_CONTROL)

        // Process
        model.addAttribute("listCardNodes", viewDtoConverterService!!.convNodeSummaryCards(listNode))

        // Return
        return G_BASE_PATH + "/NodeList"
    }

    @GetMapping("/{id}")
    fun dispNode(model: Model, @PathVariable("id") intNodeId: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val node: Node?

        // Init
        node = iotNodeService!!.getNode(intNodeId.toLong())

        // Process
        model.addAttribute("cardNodeInfo", NodeInfoCardDto(node))
        model.addAttribute("listCardNodeItems", viewDtoConverterService!!.convNodeItemCards(node.getNodeItems()))

        // Return
        return G_BASE_PATH + "/NodeInfo"
    }

    @GetMapping("/item/{id}")
    fun dispNodeItemHistory(model: Model, @PathVariable("id") intNodeItemId: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val nodeItem: NodeItem?
        val listNodeItemHistory: List<NodeItemHistory?>?

        // Init
        nodeItem = iotNodeService!!.getNodeItem(intNodeItemId.toLong())
        listNodeItemHistory = iotNodeService.getNodeItemHistory(intNodeItemId.toLong())

        // Process
        model.addAttribute("cardNodeItemHistory", NodeItemHistoryCardDto(nodeItem, listNodeItemHistory))

        // Return
        return G_BASE_PATH + "/NodeItemInfo"
    }
}