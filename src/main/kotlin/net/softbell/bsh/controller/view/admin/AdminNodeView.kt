package net.softbell.bsh.controller.view.admin

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.view.admin.NodeManageInfoCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 관리자 노드 관리 뷰 컨트롤러
 */
@Controller
@RequestMapping("/admin/node/")
class AdminNodeView {
    // Global Field
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/node"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1
    @Autowired private lateinit var centerService: CenterService

    // 노드 정보 수정페이지 출력
    @GetMapping("modify/{id}")
    fun dispNodeModify(model: Model, @AuthenticationPrincipal member: Member,
                       @PathVariable("id") nodeId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val node = iotNodeService.getNode(nodeId) ?: return G_BASE_REDIRECT_URL

        // Process
        model.addAttribute("cardNodeInfo", NodeManageInfoCardDto(node))
        model.addAttribute("listCardNodeItems", viewDtoConverterService.convNodeManageItemCards(node.nodeItems))

        // Return
        return "services/admin/NodeModify"
    }

    // 노드 비활성화 처리
    @PostMapping("disable")
    fun procNodeDisable(model: Model, @AuthenticationPrincipal member: Member,
                        @RequestParam("intNodeId") intNodeId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Log
        logger.info("$intNodeId 노드 비활성화 ")

        // Process
        return if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.DISABLE))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 노드 활성화 처리
    @PostMapping("enable")
    fun procNodeEnable(model: Model, @AuthenticationPrincipal member: Member,
                       @RequestParam("intNodeId") intNodeId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Log
        logger.info("$intNodeId 노드 활성화 ")

        // Process
        return if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.ENABLE))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 노드 제한 처리
    @PostMapping("reject")
    fun procNodeReject(model: Model, @AuthenticationPrincipal member: Member,
                       @RequestParam("intNodeId") intNodeId: Long): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Log
        logger.info("$intNodeId 노드 승인 거절 ")

        // Process
        return if (iotNodeService.setNodeEnableStatus(intNodeId, EnableStatusRule.REJECT))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/{id}")
    fun procNodeModify(model: Model, @AuthenticationPrincipal member: Member,
                       @PathVariable("id") nodeId: Long,
                       @RequestParam("alias") alias: String): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Process
        return if (iotNodeService.setNodeAlias(nodeId, alias))
            "$G_BASE_REDIRECT_URL/modify/$nodeId"
        else
            "$G_BASE_REDIRECT_URL/modify/$nodeId?error"
    }

    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/item/{id}")
    fun procNodeItemModify(model: Model, @AuthenticationPrincipal member: Member,
                           @PathVariable("id") nodeItemId: Long,
                           @RequestParam("id") nodeId: Long,
                           @RequestParam("alias") alias: String): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Process
        return if (iotNodeService.setNodeItemAlias(nodeItemId, alias))
            "$G_BASE_REDIRECT_URL/modify/$nodeId"
        else
            "$G_BASE_REDIRECT_URL/modify/$nodeId?error"
    }

    companion object : KLogging()
}