package net.softbell.bsh.controller.view.general

import lombok.AllArgsConstructor
import net.softbell.bsh.domain.entity.MemberInterlockToken
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeActionItem
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.dto.request.IotActionDto
import net.softbell.bsh.dto.view.general.ActionInfoCardDto
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.InterlockService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/action")
class ActionView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/general"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"
    private val viewDtoConverterService: ViewDtoConverterService? = null
    private val iotActionService: IotActionServiceV1? = null
    private val centerService: CenterService? = null
    private val interlockService: InterlockService? = null
    @GetMapping
    fun dispIndex(model: Model, auth: Authentication): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val listNodeAction: List<NodeAction?>?

        // Init
        listNodeAction = iotActionService!!.getAllNodeActions(auth)

        // Process
        model.addAttribute("listCardActions", viewDtoConverterService!!.convActionSummaryCards(listNodeAction))

        // Return
        return G_BASE_PATH + "/Action"
    }

    @GetMapping("/{id}")
    fun dispAction(model: Model, auth: Authentication, request: HttpServletRequest, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val nodeAction: NodeAction?
        val listNodeItem: List<NodeItem?>?
        val listMemberInterlockToken: List<MemberInterlockToken?>?
        var baseUrl: String

        // Init
        nodeAction = iotActionService!!.getNodeAction(auth, actionId)
        listNodeItem = iotActionService.getAvailableNodeItem(auth)
        listMemberInterlockToken = interlockService!!.getAllTokens(auth)
        baseUrl = request.getRequestURL().toString()
        baseUrl = baseUrl.substring(0, baseUrl.indexOf("/", 8))
        for (actionItem: NodeActionItem in nodeAction.getNodeActionItems()) listNodeItem.remove(actionItem.getNodeItem())

        // Process
        model.addAttribute("cardActionInfo", ActionInfoCardDto(nodeAction))
        model.addAttribute("listCardInterlocks", viewDtoConverterService!!.convActionInterlockTokenCards(listMemberInterlockToken))
        model.addAttribute("listCardItems", viewDtoConverterService.convActionItemCards<T>(nodeAction.getNodeActionItems()))
        model.addAttribute("baseURL", baseUrl)

        // Return
        return G_BASE_PATH + "/ActionInfo"
    }

    @GetMapping("/modify/{id}")
    fun dispActionModify(model: Model, auth: Authentication, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val nodeAction: NodeAction?
        val listNodeItem: List<NodeItem?>?

        // Init
        nodeAction = iotActionService!!.getNodeAction(auth, actionId)
        listNodeItem = iotActionService.getAvailableNodeItem(auth)
        for (actionItem: NodeActionItem in nodeAction.getNodeActionItems()) listNodeItem.remove(actionItem.getNodeItem())

        // Process
        model.addAttribute("cardActionInfo", ActionInfoCardDto(nodeAction))
        model.addAttribute("listCardItemActives", viewDtoConverterService!!.convActionItemCards<T>(nodeAction.getNodeActionItems()))
        model.addAttribute("listCardItems", viewDtoConverterService.convActionItemCards(listNodeItem))

        // Return
        return G_BASE_PATH + "/ActionModify"
    }

    @GetMapping("/create")
    fun dispCreate(model: Model, auth: Authentication): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val listNodeItem: List<NodeItem?>?

        // Init
        listNodeItem = iotActionService!!.getAvailableNodeItem(auth)

        // Process
        model.addAttribute("listCardItems", viewDtoConverterService!!.convActionItemCards(listNodeItem))

        // Return
        return G_BASE_PATH + "/ActionCreate"
    }

    @PostMapping("/create")
    fun procCreate(model: Model?, auth: Authentication,
                   iotActionDto: IotActionDto): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotActionService!!.createAction(auth, iotActionDto)

        // Return
        if (isSuccess) return "redirect:/action" else return "redirect:/action?error"
    }

    @PostMapping("/modify/{id}")
    fun procModify(model: Model?, auth: Authentication?,
                   @PathVariable("id") actionId: Long,
                   iotActionDto: IotActionDto): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotActionService!!.modifyAction(auth, actionId, iotActionDto)

        // Return
        if (isSuccess) return "redirect:/action" else return "redirect:/action/modify/" + actionId + "?error"
    }

    @PostMapping("/delete/{id}")
    fun procDelete(model: Model?, auth: Authentication?, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.getSetting().getIotAction() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotActionService!!.deleteAction(auth, actionId)

        // Return
        if (isSuccess) return "redirect:/action" else return "redirect:/action/modify/" + actionId + "?error"
    }
}