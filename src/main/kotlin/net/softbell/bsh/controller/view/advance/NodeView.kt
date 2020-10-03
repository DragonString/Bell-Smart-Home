package net.softbell.bsh.controller.view.advance

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.dto.view.advance.NodeInfoCardDto
import net.softbell.bsh.dto.view.advance.NodeItemHistoryCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 뷰 컨트롤러
 */
@Controller
@RequestMapping("/node")
class NodeView {
    // Global Field
    private val G_BASE_PATH: String = "services/advance"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1
    @Autowired private lateinit var centerService: CenterService

    @GetMapping
    fun dispIndex(model: Model, auth: Authentication,
                  @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                  @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNode = iotNodeService.getAllNodes(auth, GroupRole.MANUAL_CONTROL)

        // Process
        model.addAttribute("listCardNodes", viewDtoConverterService.convNodeSummaryCards(listNode))

        // Return
        return "$G_BASE_PATH/NodeList"
    }

    @GetMapping("/{id}")
    fun dispNode(model: Model, @PathVariable("id") intNodeId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val node = iotNodeService.getNode(intNodeId)

        // Process
        model.addAttribute("cardNodeInfo", node?.let { NodeInfoCardDto(it) })
        if (node != null) {
            model.addAttribute("listCardNodeItems", viewDtoConverterService.convNodeItemCards(node.nodeItems))
        }

        // Return
        return "$G_BASE_PATH/NodeInfo"
    }

    @GetMapping("/item/{id}")
    fun dispNodeItemHistory(model: Model, @PathVariable("id") intNodeItemId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val nodeItem = iotNodeService.getNodeItem(intNodeItemId)
        val listNodeItemHistory = iotNodeService.getNodeItemHistory(intNodeItemId)

        // Process
        model.addAttribute("cardNodeItemHistory", nodeItem?.let { NodeItemHistoryCardDto(it, listNodeItemHistory) })

        // Return
        return "$G_BASE_PATH/NodeItemInfo"
    }
}