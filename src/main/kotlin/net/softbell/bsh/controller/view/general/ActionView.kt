package net.softbell.bsh.controller.view.general

import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.dto.request.IotActionDto
import net.softbell.bsh.dto.view.general.ActionInfoCardDto
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.InterlockService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

/**
 * @author : Bell(bell@softbell.net)
 * @description : 모니터 뷰 컨트롤러
 */
@Controller
@RequestMapping("/action")
class ActionView {
    // Global Field
    private val G_BASE_PATH: String = "services/general"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotActionService: IotActionServiceV1
    @Autowired private lateinit var centerService: CenterService
    @Autowired private lateinit var interlockService: InterlockService

    @GetMapping
    fun dispIndex(model: Model, auth: Authentication): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNodeAction = iotActionService.getAllNodeActions(auth)

        // Process
        model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction))

        // Return
        return "$G_BASE_PATH/Action"
    }

    @GetMapping("/{id}")
    fun dispAction(model: Model, auth: Authentication, request: HttpServletRequest, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val nodeAction = iotActionService.getNodeAction(auth, actionId)
        val listNodeItem: MutableList<NodeItem> = iotActionService.getAvailableNodeItem(auth) as MutableList<NodeItem>
        val listMemberInterlockToken = interlockService.getAllTokens(auth)
        var baseUrl = request.requestURL.toString()

        baseUrl = baseUrl.substring(0, baseUrl.indexOf("/", 8))
        if (nodeAction != null)
            for (actionItem in nodeAction.nodeActionItems)
                listNodeItem.remove(actionItem.nodeItem)

        // Process
        model.addAttribute("cardActionInfo", nodeAction?.let { ActionInfoCardDto(it) })
        model.addAttribute("listCardInterlocks", viewDtoConverterService.convActionInterlockTokenCards(listMemberInterlockToken))
        if (nodeAction != null)
            model.addAttribute("listCardItems", viewDtoConverterService.convActionItemCards(nodeAction.nodeActionItems))
        model.addAttribute("baseURL", baseUrl)

        // Return
        return "$G_BASE_PATH/ActionInfo"
    }

    @GetMapping("/modify/{id}")
    fun dispActionModify(model: Model, auth: Authentication, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val nodeAction = iotActionService.getNodeAction(auth, actionId)
        val listNodeItem = iotActionService.getAvailableNodeItem(auth) as MutableList<NodeItem>

        if (nodeAction != null)
            for (actionItem in nodeAction.nodeActionItems)
                listNodeItem.remove(actionItem.nodeItem)

        // Process
        model.addAttribute("cardActionInfo", nodeAction?.let { ActionInfoCardDto(it) })
        if (nodeAction != null)
            model.addAttribute("listCardItemActives", viewDtoConverterService.convActionItemCards(nodeAction.nodeActionItems))
        model.addAttribute("listCardItems", viewDtoConverterService.convActionItemCards(listNodeItem))

        // Return
        return "$G_BASE_PATH/ActionModify"
    }

    @GetMapping("/create")
    fun dispCreate(model: Model, auth: Authentication): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNodeItem = iotActionService.getAvailableNodeItem(auth)

        // Process
        model.addAttribute("listCardItems", viewDtoConverterService.convActionItemCards(listNodeItem))

        // Return
        return "$G_BASE_PATH/ActionCreate"
    }

    @PostMapping("/create")
    fun procCreate(model: Model, auth: Authentication,
                   iotActionDto: IotActionDto): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotActionService.createAction(auth, iotActionDto)

        // Return
        return if (isSuccess)
            "redirect:/action"
        else
            "redirect:/action?error"
    }

    @PostMapping("/modify/{id}")
    fun procModify(model: Model, auth: Authentication,
                   @PathVariable("id") actionId: Long,
                   iotActionDto: IotActionDto): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotActionService.modifyAction(auth, actionId, iotActionDto)

        // Return
        return if (isSuccess)
            "redirect:/action"
        else
            "redirect:/action/modify/$actionId?error"
    }

    @PostMapping("/delete/{id}")
    fun procDelete(model: Model, auth: Authentication, @PathVariable("id") actionId: Long): String {
        // Exception
        if (centerService.setting.iotAction != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotActionService.deleteAction(auth, actionId)

        // Return
        return if (isSuccess)
            "redirect:/action"
        else
            "redirect:/action/modify/$actionId?error"
    }
}