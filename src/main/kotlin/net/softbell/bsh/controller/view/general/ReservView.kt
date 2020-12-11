package net.softbell.bsh.controller.view.general

import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.dto.request.IotReservDto
import net.softbell.bsh.dto.view.general.ReservInfoCardDto
import net.softbell.bsh.iot.service.v1.IotReservServiceV1
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

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예약 뷰 컨트롤러
 */
@Controller
@RequestMapping("/reserv")
class ReservView {
    // Global Field
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotReservService: IotReservServiceV1
    @Autowired private lateinit var centerService: CenterService

    @GetMapping
    fun dispIndex(model: Model, @AuthenticationPrincipal member: Member): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listReserv = iotReservService.getPrivilegesReservs(member)

        // Process
        model.addAttribute("listCardReservs", viewDtoConverterService.convReservSummaryCards(listReserv))

        // Return
        return "services/general/Reserv"
    }

    @GetMapping("/{id}")
    fun dispReserv(model: Model, @AuthenticationPrincipal member: Member,
                   @PathVariable("id") reservId: Long): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val nodeReserv = iotReservService.getPrivilegesReserv(member, reservId)

        // Process
        if (nodeReserv != null) {
            model.addAttribute("cardReservInfo", nodeReserv.let { ReservInfoCardDto(it) })
            model.addAttribute("listCardActionActives", viewDtoConverterService.convReservActionCards(nodeReserv.nodeReservActions))
        }

        // Return
        return "services/general/ReservInfo"
    }

    @GetMapping("/modify/{id}")
    fun dispReservModify(model: Model, @AuthenticationPrincipal member: Member,
                         @PathVariable("id") reservId: Long): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Field

        // Init
        val nodeReserv = iotReservService.getPrivilegesReserv(member, reservId)
        val listNodeAction = iotReservService.getAvailableAction(member) as MutableList<NodeAction>
        if (nodeReserv != null) {
            for (reservActionItem in nodeReserv.nodeReservActions)
                listNodeAction.remove(reservActionItem.nodeAction)

            // Process
            model.addAttribute("cardReservInfo", ReservInfoCardDto(nodeReserv))
            model.addAttribute("listCardActionActives", viewDtoConverterService.convReservActionCards(nodeReserv.nodeReservActions))
        }
        model.addAttribute("listCardActions", viewDtoConverterService.convReservActionCards(listNodeAction))

        // Return
        return "services/general/ReservModify"
    }

    @GetMapping("/create")
    fun dispCreate(model: Model, @AuthenticationPrincipal member: Member): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNodeAction = iotReservService.getAvailableAction(member)

        // Process
        model.addAttribute("listCardActions", viewDtoConverterService.convReservActionCards(listNodeAction))

        // Return
        return "services/general/ReservCreate"
    }

    @PostMapping("/create")
    fun procCreate(model: Model, @AuthenticationPrincipal member: Member,
                   iotReservationDto: IotReservDto): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotReservService.createReservation(member, iotReservationDto)

        // Return
        return if (isSuccess)
            "redirect:/reserv"
        else
            "redirect:/reserv?error"
    }

    @PostMapping("/modify/{id}")
    fun procModify(model: Model, @AuthenticationPrincipal member: Member,
                   @PathVariable("id") reservId: Long,
                   iotReservationDto: IotReservDto): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotReservService.modifyPrivilegesReservation(member, reservId, iotReservationDto)

        // Return
        return if (isSuccess)
            "redirect:/reserv"
        else
            "redirect:/reserv/modify/$reservId?error"
    }

    @PostMapping("/delete/{id}")
    fun procDelete(model: Model, @AuthenticationPrincipal member: Member,
                   @PathVariable("id") reservId: Long): String {
        // Exception
        if (centerService.setting.iotReserv != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val isSuccess = iotReservService.deletePrivilegesReserv(member, reservId)

        // Return
        return if (isSuccess)
            "redirect:/reserv"
        else
            "redirect:/reserv/modify/$reservId?error"
    }
}