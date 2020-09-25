package net.softbell.bsh.controller.view.admin

import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.dto.view.admin.NodeManageInfoCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ViewDtoConverterService
import net.softbell.bsh.util.BellLog
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 노드 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/node/")
class AdminNodeView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/admin"
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/node"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"
    private val viewDtoConverterService: ViewDtoConverterService? = null
    private val memberService: MemberService? = null
    private val iotNodeService: IotNodeServiceV1? = null
    private val centerService: CenterService? = null

    // 노드 정보 수정페이지 출력
    @GetMapping("modify/{id}")
    fun dispNodeModify(model: Model, principal: Principal, @PathVariable("id") nodeId: Long): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())
        val node: Node?

        // Init
        node = iotNodeService!!.getNode(nodeId)

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        model.addAttribute("cardNodeInfo", NodeManageInfoCardDto(node))
        model.addAttribute("listCardNodeItems", viewDtoConverterService!!.convNodeManageItemCards(node.getNodeItems()))

        // Return
        return G_BASE_PATH + "/NodeModify"
    }

    // 노드 비활성화 처리
    @PostMapping("disable")
    fun procNodeDisable(model: Model?, principal: Principal,
                        @RequestParam("intNodeId") intNodeId: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Log
        log.info(BellLog.getLogHead() + intNodeId + " 노드 비활성화 ")

        // Process
        if (iotNodeService!!.setNodeEnableStatus(intNodeId.toLong(), EnableStatusRule.DISABLE)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 노드 활성화 처리
    @PostMapping("enable")
    fun procNodeEnable(model: Model?, principal: Principal,
                       @RequestParam("intNodeId") intNodeId: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Log
        log.info(BellLog.getLogHead() + intNodeId + " 노드 활성화 ")

        // Process
        if (iotNodeService!!.setNodeEnableStatus(intNodeId.toLong(), EnableStatusRule.ENABLE)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 노드 제한 처리
    @PostMapping("reject")
    fun procNodeReject(model: Model?, principal: Principal,
                       @RequestParam("intNodeId") intNodeId: Int): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Log
        log.info(BellLog.getLogHead() + intNodeId + " 노드 승인 거절 ")

        // Process
        if (iotNodeService!!.setNodeEnableStatus(intNodeId.toLong(), EnableStatusRule.REJECT)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/{id}")
    fun procNodeModify(model: Model?, principal: Principal, @PathVariable("id") nodeId: Long,
                       @RequestParam("alias") alias: String?): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (iotNodeService!!.setNodeAlias(nodeId, alias)) return G_BASE_REDIRECT_URL + "/modify/" + nodeId else return G_BASE_REDIRECT_URL + "/modify/" + nodeId + "?error"
    }

    // 노드 정보 수정 프로세스 수행
    @PostMapping("modify/item/{id}")
    fun procNodeItemModify(model: Model?, principal: Principal, @PathVariable("id") nodeItemId: Long,
                           @RequestParam("id") nodeId: Long,
                           @RequestParam("alias") alias: String?): String {
        // Exception
        if (centerService.getSetting().getIotNode() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (iotNodeService!!.setNodeItemAlias(nodeItemId, alias)) return G_BASE_REDIRECT_URL + "/modify/" + nodeId else return G_BASE_REDIRECT_URL + "/modify/" + nodeId + "?error"
    }
}