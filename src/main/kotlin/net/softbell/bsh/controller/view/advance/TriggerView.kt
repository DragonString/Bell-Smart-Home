package net.softbell.bsh.controller.view.advance

import net.softbell.bsh.domain.TriggerStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.dto.request.IotTriggerDto
import net.softbell.bsh.dto.view.advance.TriggerInfoCardDto
import net.softbell.bsh.dto.view.general.ActionSummaryCardDto
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import net.softbell.bsh.iot.service.v1.IotTriggerServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 뷰 컨트롤러
 */
@Controller
@RequestMapping("/trigger")
class TriggerView {
    // Global Field
    private val G_BASE_PATH: String = "services/advance"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotTriggerService: IotTriggerServiceV1
    @Autowired private lateinit var iotActionService: IotActionServiceV1
    @Autowired private lateinit var centerService: CenterService

    @GetMapping
    fun dispIndex(model: Model, @AuthenticationPrincipal member: Member): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listTrigger = iotTriggerService.getPrivilegesTriggers(member)

        // Process
        model.addAttribute("listCardTriggers", viewDtoConverterService.convTriggerSummaryCards(listTrigger))

        // Return
        return "$G_BASE_PATH/Trigger"
    }

    @GetMapping("/create")
    fun dispCreate(model: Model, @AuthenticationPrincipal member: Member): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNodeAction = iotActionService.getPrivilegesNodeActions(member)

        // Process
        model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction))
        //model.addAttribute("listCardNodeItems", viewDtoConverterService.convTriggerItemCards(iotNodeService.getAllNodeItems(auth)));

        // Return
        return "$G_BASE_PATH/TriggerCreate"
    }

    @GetMapping("/{id}")
    fun dispTrigger(model: Model, @AuthenticationPrincipal member: Member,
                    @PathVariable("id") triggerId: Long): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val nodeTrigger = iotTriggerService.getPrivilegesTrigger(member, triggerId) ?: return "redirect:/trigger?err"
        val listCardActionsAll: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsOccurAndRestore: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsOccur: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsRestore: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsError: MutableList<ActionSummaryCardDto> = ArrayList()

        // Process
        for (entity in nodeTrigger.nodeTriggerActions) {
            when (entity.triggerStatus) {
                TriggerStatusRule.ALL -> listCardActionsAll.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.OCCUR_AND_RESTORE -> listCardActionsOccurAndRestore.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.OCCUR -> listCardActionsOccur.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.RESTORE -> listCardActionsRestore.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.ERROR -> listCardActionsError.add(ActionSummaryCardDto(entity.nodeAction))
            }
        }

        model.addAttribute("cardTriggerInfo", TriggerInfoCardDto(nodeTrigger))
        model.addAttribute("listCardActionsAll", listCardActionsAll)
        model.addAttribute("listCardActionsOccurAndRestore", listCardActionsOccurAndRestore)
        model.addAttribute("listCardActionsOccur", listCardActionsOccur)
        model.addAttribute("listCardActionsRestore", listCardActionsRestore)
        model.addAttribute("listCardActionsError", listCardActionsError)

        // Return
        return "$G_BASE_PATH/TriggerInfo"
    }

    @GetMapping("/modify/{id}")
    fun dispTriggerModify(model: Model, @AuthenticationPrincipal member: Member,
                          @PathVariable("id") triggerId: Long): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Field


        // Init
        val nodeTrigger = iotTriggerService.getPrivilegesTrigger(member, triggerId) ?: return "redirect:/trigger?err"
        val listNodeAction = iotActionService.getPrivilegesNodeActions(member) as MutableList<NodeAction>
        val listCardActionsAll: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsOccurAndRestore: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsOccur: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsRestore: MutableList<ActionSummaryCardDto> = ArrayList()
        val listCardActionsError: MutableList<ActionSummaryCardDto> = ArrayList()

        // Process
        for (entity in nodeTrigger.nodeTriggerActions) {
            when (entity.triggerStatus) {
                TriggerStatusRule.ALL -> listCardActionsAll.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.OCCUR_AND_RESTORE -> listCardActionsOccurAndRestore.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.OCCUR -> listCardActionsOccur.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.RESTORE -> listCardActionsRestore.add(ActionSummaryCardDto(entity.nodeAction))
                TriggerStatusRule.ERROR -> listCardActionsError.add(ActionSummaryCardDto(entity.nodeAction))
            }
            listNodeAction.remove(entity.nodeAction)
        }

        model.addAttribute("cardTriggerInfo", TriggerInfoCardDto(nodeTrigger))
        model.addAttribute("listCardActionsAll", listCardActionsAll)
        model.addAttribute("listCardActionsOccurAndRestore", listCardActionsOccurAndRestore)
        model.addAttribute("listCardActionsOccur", listCardActionsOccur)
        model.addAttribute("listCardActionsRestore", listCardActionsRestore)
        model.addAttribute("listCardActionsError", listCardActionsError)
        model.addAttribute("listCardActions", viewDtoConverterService.convActionSummaryCards(listNodeAction))

        // Return
        return "$G_BASE_PATH/TriggerModify"
    }

    @PostMapping("/create")
    fun procCreate(@AuthenticationPrincipal member: Member,
                   iotTriggerDto: IotTriggerDto): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Process
        val isSuccess = iotTriggerService.createTrigger(member, iotTriggerDto)

        // Return
        return if (isSuccess)
            "redirect:/trigger"
        else
            "redirect:/trigger?err"
    }

    @PostMapping("/modify/{id}")
    fun procModify(@AuthenticationPrincipal member: Member,
                   @PathVariable("id") triggerId: Long, iotTriggerDto: IotTriggerDto): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Process
        val isSuccess = iotTriggerService.modifyPrivilegesTrigger(member, triggerId, iotTriggerDto)

        // Return
        return if (isSuccess)
            "redirect:/trigger/$triggerId"
        else
            "redirect:/trigger/$triggerId?err"
    }

    @PostMapping("/delete/{id}")
    fun procDelete(@AuthenticationPrincipal member: Member,
                   @PathVariable("id") triggerId: Long): String {
        // Exception
        if (centerService.setting.iotTrigger != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Process
        val isSuccess = iotTriggerService.deletePrivilegesTrigger(member, triggerId)

        // Return
        return if (isSuccess)
            "redirect:/trigger"
        else
            "redirect:/trigger?err"
    }
}